package fr.univ.lille1.ftp.server.request;

import java.io.IOException;
import java.net.Socket;

public abstract class FtpRequest {

	protected Socket socket;

	public FtpRequest(Socket socket) {
		this.socket = socket;
	}

	public abstract FtpResponse process() throws IOException;
}
