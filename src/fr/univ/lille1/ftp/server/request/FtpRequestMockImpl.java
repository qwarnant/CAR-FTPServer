package fr.univ.lille1.ftp.server.request;

import java.net.Socket;

public class FtpRequestMockImpl extends FtpRequest {

	public FtpRequestMockImpl(Socket socket) {
		super(socket);
		// TODO Auto-generated constructor stub
	}

	@Override
	public FtpResponse process() {
		return null;
	}
	
	public Socket getCurrentConnection() {
		return this.socket;
	}

}
