package fr.univ.lille1.ftp.server.request;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

import fr.univ.lille1.ftp.server.FtpUserManager;
import fr.univ.lille1.ftp.util.FtpConstants;

public class FtpPassRequest extends FtpRequest {

	public FtpPassRequest(Socket socket) {
		super(socket);
	}

	@Override
	public FtpResponse process() throws IOException {
		// Get the input stream
		InputStreamReader isr = new InputStreamReader(
				socket.getInputStream());
		BufferedReader br = new BufferedReader(isr);

		// Read the request type
		String line = br.readLine();

		// Get the user name
		String password = line.substring(5, line.length());

		if (!FtpUserManager.getInstance().containsUser(password)) {
			return new FtpResponse(
					FtpConstants.FTP_ERROR_INVALID_USER_PWD_CODE,
					FtpConstants.FTP_ERROR_INVALID_USER_PWD_MSG);
		}

		return new FtpResponse(FtpConstants.FTP_RESPONSE_NEED_USER_CODE,
				FtpConstants.FTP_RESPONSE_NEED_USER_MSG);
	}

}
