package fr.univ.lille1.ftp.server.request;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

import fr.univ.lille1.ftp.server.FtpUserManager;
import fr.univ.lille1.ftp.util.FtpConstants;

public class FtpPassRequest extends FtpRequest {

	private String username;

	public FtpPassRequest(Socket socket, String username) {
		super(socket);
		this.username = username;
	}

	@Override
	public FtpResponse process() throws IOException {
		
		if(this.username == null) {
			//TODO 
		}
		
		// Get the input stream
		InputStreamReader isr = new InputStreamReader(socket.getInputStream());
		BufferedReader br = new BufferedReader(isr);

		// Read the request type
		String line = br.readLine();

		// Get the user name
		String password = line.substring(5, line.length());

		if (!FtpUserManager.getInstance().checkPassword(username, password)) {
			return new FtpResponse(
					FtpConstants.FTP_ERROR_INVALID_USER_PWD_CODE,
					FtpConstants.FTP_ERROR_INVALID_USER_PWD_MSG);
		}

		return new FtpResponse(FtpConstants.FTP_RESPONSE_LOGIN_OK_CODE,
				FtpConstants.FTP_RESPONSE_LOGIN_OK_MSG);
	}

}
