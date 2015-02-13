package fr.univ.lille1.ftp.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import fr.univ.lille1.ftp.util.FtpConstants;

public class FtpServer {

	private ServerSocket serverSocket;
	private static String ftpDirectory;

	public FtpServer(String ftpDirectory, int ftpPort) throws IOException {
		this.ftpDirectory = ftpDirectory;
		this.serverSocket = new ServerSocket(ftpPort);
	}

	public void close() {
		if (this.serverSocket != null)
			try {
				this.serverSocket.close();
			} catch (IOException e) {
				if (FtpConstants.DEBUG_ENABLED) {
					System.out.println(e.getMessage());
				}
			}
	}

	public Socket accept() throws IOException {
		if (this.serverSocket == null)
			return null;
		return this.serverSocket.accept();
	}

	public static String getFtpDirectory() {
		return ftpDirectory;
	}

}
