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
import fr.univ.lille1.ftp.server.request.data.FtpNlstRequest;
import fr.univ.lille1.ftp.util.FtpConstants;

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
    private DataOutputStream dataOutStream;

    private List<FtpRequest> history;


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

            if(FtpConstants.DEBUG_ENABLED) {
                System.out.println("End of the FTP Thread #" + this.id);
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
			String command = commandLine.split(" ")[0];

            if(FtpConstants.DEBUG_ENABLED) {
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

				this.dataSocket = new Socket(rport.getRemoteIp(), rport.getRemotePort());

                if(FtpConstants.DEBUG_ENABLED) {
                    System.out.println("Remote data connection on : " + this.dataSocket.getRemoteSocketAddress());
                }

			} else if (command.equals(FtpConstants.FTP_CMD_PASV)) {

				// Make a PASV request
				FtpPasvRequest rpasv = new FtpPasvRequest(commandLine);
				this.storeAndExecute(rpasv);

                ServerSocket socket = new ServerSocket(rpasv.getLocalPort());
                this.dataSocket = socket.accept();

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

			} else if (command.equals(FtpConstants.FTP_CMD_PWD) || command.equals(FtpConstants.FTP_CMD_XPWD)) {

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

			} else if (command.equals(FtpConstants.FTP_CMD_NLST) || command.equals(FtpConstants.FTP_CMD_LIST)) {

                // Make a NLST request
                FtpNlstRequest rnlst = new FtpNlstRequest(commandLine,
                        this.currentDirectory,
                        this.currentType,
                        this.dataSocket);

                FtpResponse prepareResponse = rnlst.prepare();
                this.controlOutStream.writeBytes(prepareResponse.toString());

                this.storeAndExecute(rnlst);

            } else if(command.equals(FtpConstants.FTP_CMD_MKD) || command.equals(FtpConstants.FTP_CMD_XMKD)){

                // Make a MKD request
                FtpMkdRequest rmkd = new FtpMkdRequest(commandLine, this.currentDirectory, this.currentUsername);
                this.storeAndExecute(rmkd);

            } else if(command.equals(FtpConstants.FTP_CMD_RMD) || command.equals(FtpConstants.FTP_CMD_XRMD)){

                // Make a RMD request
                FtpRmdRequest rmkd = new FtpRmdRequest(commandLine, this.currentDirectory, this.currentUsername);
                this.storeAndExecute(rmkd);

            } else if(command.equals(FtpConstants.FTP_CMD_QUIT)){

                // Make a QUIT request
                FtpQuitRequest rquit = new FtpQuitRequest(commandLine, this);
                this.storeAndExecute(rquit);

			} else {
				// Return a FTP response error
				FtpResponse response = new FtpResponse(
						FtpConstants.FTP_ERR_INVALID_COMMAND_CODE,
						FtpConstants.FTP_ERR_INVALID_COMMAND_MSG);
				controlOutStream.writeBytes(response.toString());
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
		controlOutStream.writeBytes(response.toString());
		controlOutStream.flush();
	}

    public void setRunning(boolean running) {
        this.running = running;
    }
}
