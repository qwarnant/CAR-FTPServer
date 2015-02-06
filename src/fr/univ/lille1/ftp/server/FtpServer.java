package fr.univ.lille1.ftp.server;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import fr.univ.lille1.ftp.server.request.FtpResponse;
import fr.univ.lille1.ftp.util.FtpConstants;

public class FtpServer {
	
	/**
	 * TODO : TYPE A => transfert suivant en texte & TYPE I => transfert suivant en binaire
	 */
		
	private static ServerSocket serverSocket;
	private static String ftpDirectory;

	private FtpServer(String ftpDirectory, int ftpPort) throws IOException {
		this.ftpDirectory = ftpDirectory;
		this.serverSocket = new ServerSocket(ftpPort);
	}
	
	public void close() {
		//TODO
	}

    public static String getFtpDirectory() {
        return ftpDirectory;
    }
	
	public static void main(String[] args) {
		
		if(args.length == 0) {
			throw new IllegalArgumentException("Usage : todo");
		}
		
		FtpServer server = null;
		
		try {

			server = new FtpServer(args[0], FtpConstants.FTP_SERVER_PORT);
			while(true) {
				
				// Accept a new connection
				Socket newSocket = serverSocket.accept();
				
				// Custom thread for our new client
				new FtpThread(newSocket).run();
			}

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if(server != null) {
				server.close();
			}
		}
		
		
	}


}
