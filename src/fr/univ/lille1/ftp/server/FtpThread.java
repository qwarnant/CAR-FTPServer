package fr.univ.lille1.ftp.server;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import fr.univ.lille1.ftp.server.request.FtpPassRequest;
import fr.univ.lille1.ftp.server.request.FtpRequest;
import fr.univ.lille1.ftp.server.request.FtpResponse;
import fr.univ.lille1.ftp.server.request.FtpUserRequest;
import fr.univ.lille1.ftp.util.FtpConstants;

public class FtpThread extends Thread {

	private String username;
	
	private Socket socket;
	private List<FtpRequest> history;

	public FtpThread(Socket socket) {
		super();
		this.socket = socket;
		this.history = new ArrayList<FtpRequest>();
	}

	public void storeAndExecute(FtpRequest request) throws IOException {
		history.add(request);
		FtpResponse response = request.process();
		
		DataOutputStream br = new DataOutputStream(socket.getOutputStream());
		br.writeBytes(response.getCode() + " " + response.getMessage());
	}

	@Override
	public void run() {
		while (true) {
			this.handleInputRequest();
		}
	}

	public void handleInputRequest() {

		try {

			// Get the input stream
			InputStreamReader isr = new InputStreamReader(
					socket.getInputStream());
			BufferedReader br = new BufferedReader(isr);

			// Read the request type
			String command = br.readLine().substring(0, 4);
			command.trim();
			System.out.println("Receive request : " + command);

			if (command.equals(FtpConstants.FTP_CMD_USER)) {

				// Make a USER request
				FtpUserRequest ruser = new FtpUserRequest(socket);
				this.storeAndExecute(ruser);
				this.username = ruser.getResultUsername();

			} else if (command.equals(FtpConstants.FTP_CMD_PASS)) {

				// Make a PASS request
				FtpRequest rpass = new FtpPassRequest(socket, this.username);
				this.storeAndExecute(rpass);

			} else {
				//TODO custom exception
				throw new IllegalArgumentException("Bad request name"); // Bad
																		// request
			}

		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
	}
}
