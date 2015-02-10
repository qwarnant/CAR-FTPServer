package fr.univ.lille1.ftp.server;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import fr.univ.lille1.ftp.server.request.FtpRequest;
import fr.univ.lille1.ftp.server.request.FtpResponse;
import fr.univ.lille1.ftp.server.request.control.FtpCdupRequest;
import fr.univ.lille1.ftp.server.request.control.FtpCwdRequest;
import fr.univ.lille1.ftp.server.request.control.FtpPassRequest;
import fr.univ.lille1.ftp.server.request.control.FtpPasvRequest;
import fr.univ.lille1.ftp.server.request.control.FtpPortRequest;
import fr.univ.lille1.ftp.server.request.control.FtpPwdRequest;
import fr.univ.lille1.ftp.server.request.control.FtpTypeRequest;
import fr.univ.lille1.ftp.server.request.control.FtpUserRequest;
import fr.univ.lille1.ftp.util.FtpConstants;

public class FtpThread extends Thread {

	private String currentUsername;
	private String currentRemoteIp;
	private int currentRemotePort;
	private char currentType;
	private String currentDirectory;

	private Socket controlSocket;

	private List<FtpRequest> history;

	private DataOutputStream out;

	public FtpThread(Socket socket) throws IOException {
		super();
		this.controlSocket = socket;
		this.out = new DataOutputStream(socket.getOutputStream());
		this.history = new ArrayList<FtpRequest>();
		this.currentDirectory = FtpServer.getFtpDirectory();
	}

	@Override
	public void run() {

		try {

			// Make a FTP reponse to say that the server is ready
			Date now = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
			FtpResponse response = new FtpResponse(
					FtpConstants.FTP_REP_READY_CODE,
					FtpConstants.FTP_REP_READY_MSG + sdf.format(now));

			// Print welcome message
			out.writeBytes("******************************\n");
			out.writeBytes("Welcome to my great FTP server\n");
			out.writeBytes("******************************\n");
			out.writeBytes(response.toString());

			// Starting to catch all FTP requests ...
			while (true) {
				this.handleInputRequest();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void handleInputRequest() {

		try {

			// Get the input stream
			InputStreamReader isr = new InputStreamReader(
					controlSocket.getInputStream());
			BufferedReader br = new BufferedReader(isr);

			// Read the request type
			String commandLine = br.readLine();
			String command = commandLine.substring(0, 4).trim();

			if (FtpConstants.DEBUG_ENABLED) {
				System.out.println("Receive request : " + command);
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

				this.currentRemoteIp = rport.getRemoteIp();
				this.currentRemotePort = rport.getRemotePort();
			} else if (command.equals(FtpConstants.FTP_CMD_PASV)) {

				// Make a PASV request
				FtpPasvRequest rpasv = new FtpPasvRequest(commandLine);
				this.storeAndExecute(rpasv);
				// TODO

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

			} else if (command.equals(FtpConstants.FTP_CMD_PWD)) {

				// Make a PWD request
				FtpPwdRequest rpwd = new FtpPwdRequest(commandLine,
						this.currentDirectory);
				this.storeAndExecute(rpwd);

			} else if (command.equals(FtpConstants.FTP_CMD_PWD)) {

				// Make a CDUP request
				FtpCdupRequest rcwd = new FtpCdupRequest(commandLine, "../"
						+ this.currentDirectory);
				this.storeAndExecute(rcwd);

				this.currentDirectory = rcwd.getNewCurrentDirectory();

			} else if (command.equals(FtpConstants.FTP_CMD_NLST)) {

			} else {
				// Return a FTP response error
				FtpResponse response = new FtpResponse(
						FtpConstants.FTP_ERR_INVALID_COMMAND_CODE,
						FtpConstants.FTP_ERR_INVALID_COMMAND_MSG);
				out.writeBytes(response.toString());
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void storeAndExecute(FtpRequest request) throws IOException {
		history.add(request);

		FtpResponse response = request.process();

		if (FtpConstants.DEBUG_ENABLED) {
			System.out.println(request);
			System.out.println(response);
		}

		// Write the answer to the socket
		out.writeBytes(response.toString());
	}
}
