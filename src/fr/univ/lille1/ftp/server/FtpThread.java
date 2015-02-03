package fr.univ.lille1.ftp.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import fr.univ.lille1.ftp.server.request.FtpRequest;
import fr.univ.lille1.ftp.server.request.FtpUserRequest;
import fr.univ.lille1.ftp.util.FtpConstants;

public class FtpThread extends Thread {

	private Socket socket;
	private List<FtpRequest> history;

	public FtpThread(Socket socket) {
		super();
		this.socket = socket;
		this.history = new ArrayList<FtpRequest>();
	}

	public void storeAndExecute(FtpRequest request) throws IOException {
		history.add(request);
		request.process();
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
			String s = br.readLine().substring(0, 4);
			s.trim();
			System.out.println("Receive request : " + s);

			if (s.equals(FtpConstants.FTP_CMD_USER)) {

				// Make a USER request
				FtpRequest ruser = new FtpUserRequest(socket);
				this.storeAndExecute(ruser);

			} else if (s.equals(FtpConstants.FTP_CMD_PSWD)) {

				// Make a PSWD request
				// TODO

			} else {
				throw new IllegalArgumentException("Bad request name"); // Bad
																		// request
			}

		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
	}
}
