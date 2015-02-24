package fr.univ.lille1.ftp.server;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import fr.univ.lille1.ftp.server.request.FtpRequest;
import fr.univ.lille1.ftp.server.request.FtpResponse;
import fr.univ.lille1.ftp.server.request.control.*;
import fr.univ.lille1.ftp.server.request.data.FtpListRequest;
import fr.univ.lille1.ftp.server.request.data.FtpNlstRequest;
import fr.univ.lille1.ftp.server.request.data.FtpRetrRequest;
import fr.univ.lille1.ftp.server.request.data.FtpStorRequest;
import fr.univ.lille1.ftp.util.FtpConstants;

/**
 * FtpThread is the class that handles all the ftp thread logic A FtpThread is
 * created when a new client is connected on the ftp server
 * 
 * @author Quentin Warnant
 * @version 1.0
 */

public class FtpThread extends Thread {

	public static int CURRENT_THREAD_ID = 1;

	private int id;
	private boolean running;

	private String currentUsername;
	private char currentType;
	private String currentDirectory;

	private Socket controlSocket;
	private DataOutputStream controlOutStream;

	private Socket dataSocket;
	private int dataPort;

	/**
	 * This attribute contains all history of the ftp request of the thread
	 * Reserved for later usages
	 */
	private List<FtpRequest> history;

	/**
	 * Class constructor
	 * 
	 * @param socket
	 *            Socket the new client socket connection
	 * @throws IOException
	 */
	public FtpThread(Socket socket) throws IOException {
		super();
		this.controlSocket = socket;
		this.controlOutStream = new DataOutputStream(socket.getOutputStream());
		this.history = new ArrayList<FtpRequest>();
		this.currentDirectory = FtpServer.getFtpDirectory();
		this.currentType = FtpConstants.FTP_BINARY_TYPE;

		this.running = true;
		this.id = CURRENT_THREAD_ID++;
	}

	@Override
	public void run() {

		try {
			if (FtpConstants.LOGGER_ENABLED) {
				System.out.println("Starting the FTP Thread #" + this.id);
			}

			// Make a FTP reponse to say that the server is ready
			Date now = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
			FtpResponse response = new FtpResponse(
					FtpConstants.FTP_REP_READY_CODE,
					FtpConstants.FTP_REP_READY_MSG + sdf.format(now));

			// Print welcome message
			controlOutStream.writeBytes(response.toString());

			// Starting to catch all FTP requests ...
			while (this.running) {
				this.handleInputRequest();
			}

		} catch (IOException e) {
			if (FtpConstants.LOGGER_ENABLED) {
				System.out.println("Client disconnected");
			}
		} finally {
			if (FtpConstants.LOGGER_ENABLED) {
				System.out.println("End of the FTP Thread #" + this.id);
			}
		}
	}

	/**
	 * This method handles all input requests of the ftp client
	 * <p/>
	 * If the request is not implemented in the server, the server answers with
	 * the correct error code
	 */
	public void handleInputRequest() {

		try {

			// Get the input stream
			InputStreamReader isr = new InputStreamReader(
					controlSocket.getInputStream());
			BufferedReader br = new BufferedReader(isr);

			// Read the request type
			String commandLine = br.readLine();

			// Bad writing on the socket
			if (commandLine == null) {
				return;
			}
			String command = commandLine.split(" ")[0];

			if (FtpConstants.LOGGER_ENABLED) {
				System.out.println(commandLine);
			}

			if (command.equals(FtpConstants.FTP_CMD_USER)) {

				// Make a USER request
				FtpUserRequest ruser = new FtpUserRequest(commandLine);
				this.storeAndExecute(ruser);
				this.currentUsername = ruser.getResultUsername();

			} else if (command.equals(FtpConstants.FTP_CMD_PASS)) {

				// Make a PASS request
				FtpRequest rpass = new FtpPassRequest(commandLine,
						this.currentUsername);
				this.storeAndExecute(rpass);

			} else if (command.equals(FtpConstants.FTP_CMD_PORT)) {

				// Make a PORT request
				FtpPortRequest rport = new FtpPortRequest(commandLine);
				this.storeAndExecute(rport);

				if (rport.getRemotePort() != 0 && rport.getRemoteIp() != null) {
					this.dataSocket = new Socket(rport.getRemoteIp(),
							rport.getRemotePort());
					FtpServer.getFtpLogger().info(
							"Remote data connection on : "
									+ this.dataSocket.getRemoteSocketAddress());
				}

			} else if (command.equals(FtpConstants.FTP_CMD_PASV)) {

				// Make a PASV request
				FtpPasvRequest rpasv = new FtpPasvRequest(commandLine);
				FtpResponse response = this.storeAndExecute(rpasv);

				if (rpasv.getLocalPort() != 0) {
					FtpServer.getFtpLogger().info(
							"Accept data connection from port : "
									+ rpasv.getLocalPort());

					ServerSocket socket = new ServerSocket(rpasv.getLocalPort());
					this.dataPort = rpasv.getLocalPort();

					controlOutStream.writeBytes(response.toString());
					controlOutStream.flush();

					this.dataSocket = socket.accept();
				}

			} else if (command.equals(FtpConstants.FTP_CMD_TYPE)) {

				// Make a TYPE request
				FtpTypeRequest rtype = new FtpTypeRequest(commandLine);
				this.storeAndExecute(rtype);

				this.currentType = rtype.getType();

			} else if (command.equals(FtpConstants.FTP_CMD_CWD)) {

				// Make a CWD request
				FtpCwdRequest rcwd = new FtpCwdRequest(commandLine,
						this.currentDirectory);
				this.storeAndExecute(rcwd);

				this.currentDirectory = rcwd.getNewCurrentDirectory();

			} else if (command.equals(FtpConstants.FTP_CMD_PWD)
					|| command.equals(FtpConstants.FTP_CMD_XPWD)) {

				// Make a PWD request
				FtpPwdRequest rpwd = new FtpPwdRequest(commandLine,
						this.currentDirectory);
				this.storeAndExecute(rpwd);

			} else if (command.equals(FtpConstants.FTP_CMD_CDUP)) {

				// Make a CDUP request
				FtpCdupRequest rcwd = new FtpCdupRequest(commandLine,
						this.currentDirectory);
				this.storeAndExecute(rcwd);

				this.currentDirectory = rcwd.getNewCurrentDirectory();
			} else if (command.equals(FtpConstants.FTP_CMD_LIST)) {

				// Make a LIST request
				FtpListRequest rlist = new FtpListRequest(commandLine,
						this.currentDirectory, this.currentType,
						this.dataSocket);

				FtpResponse prepareResponse = rlist.prepare();
				this.controlOutStream.writeBytes(prepareResponse.toString());

				this.storeAndExecute(rlist);
			} else if (command.equals(FtpConstants.FTP_CMD_NLST)) {

				// Make a NLST request
				FtpNlstRequest rnlst = new FtpNlstRequest(commandLine,
						this.currentDirectory, this.currentType,
						this.dataSocket);

				FtpResponse prepareResponse = rnlst.prepare();
				this.controlOutStream.writeBytes(prepareResponse.toString());

				this.storeAndExecute(rnlst);

			} else if (command.equals(FtpConstants.FTP_CMD_MKD)
					|| command.equals(FtpConstants.FTP_CMD_XMKD)) {

				// Make a MKD request
				FtpMkdRequest rmkd = new FtpMkdRequest(commandLine,
						this.currentDirectory, this.currentUsername);
				this.storeAndExecute(rmkd);

			} else if (command.equals(FtpConstants.FTP_CMD_RMD)
					|| command.equals(FtpConstants.FTP_CMD_XRMD)) {

				// Make a RMD request
				FtpRmdRequest rmkd = new FtpRmdRequest(commandLine,
						this.currentDirectory, this.currentUsername);
				this.storeAndExecute(rmkd);

			} else if (command.equals(FtpConstants.FTP_CMD_RETR)) {
				// Make a RETR request
				FtpRetrRequest rretr = new FtpRetrRequest(commandLine,
						this.currentDirectory, this.currentType,
						this.dataSocket);

				FtpResponse prepareResponse = rretr.prepare();
				this.controlOutStream.writeBytes(prepareResponse.toString());

				this.storeAndExecute(rretr);
			} else if (command.equals(FtpConstants.FTP_CMD_STOR)) {
				// Make a STOR request
				FtpStorRequest rstor = new FtpStorRequest(commandLine,
						this.currentDirectory, this.currentUsername,
						this.currentType, this.dataSocket);

				FtpResponse prepareResponse = rstor.prepare();
				this.controlOutStream.writeBytes(prepareResponse.toString());

				this.storeAndExecute(rstor);
			} else if (command.equals(FtpConstants.FTP_CMD_QUIT)) {

				// Make a QUIT request
				FtpQuitRequest rquit = new FtpQuitRequest(commandLine, this);
				this.storeAndExecute(rquit);

			} else {
				// Request unknown : Return a FTP response error
				FtpResponse response = new FtpResponse(
						FtpConstants.FTP_ERR_INVALID_COMMAND_CODE,
						FtpConstants.FTP_ERR_INVALID_COMMAND_MSG);
				controlOutStream.writeBytes(response.toString());
			}

		} catch (IOException e) {
			// Client disconnect : end of the thread and free the data port if
			// needed
			FtpServer.getFtpLogger().error(
					"Internal error on the server, closing the thread #"
							+ this.id + " with error " + e.getMessage());

			this.running = false;
			FtpPortManager.getInstance().freePort(this.dataPort);
		}
	}

	/**
	 * This method stores the request in the request history and executes the
	 * specified ftp request of the client
	 * 
	 * @param request
	 *            FtpRequest the specified ftp request
	 * @throws IOException
	 */
	public FtpResponse storeAndExecute(FtpRequest request) throws IOException {
		history.add(request);

		FtpResponse response = request.process();

		FtpServer.getFtpLogger().info(request.toString());
		FtpServer.getFtpLogger().info(response.toString());

		// We must wait the server socket connection !
		if (!(request instanceof FtpPasvRequest)) {
			// Write the answer to the socket
			controlOutStream.writeBytes(response.toString());
			controlOutStream.flush();
		}

		return response;
	}

	/**
	 * This method sets the current state of the thread
	 * 
	 * @param running
	 *            boolean true if the thread is running false otherwise
	 */
	public void setRunning(boolean running) {
		this.running = running;
	}
}
