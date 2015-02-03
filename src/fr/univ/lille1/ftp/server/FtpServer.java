package fr.univ.lille1.ftp.server;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import fr.univ.lille1.ftp.server.request.FtpRequest;
import fr.univ.lille1.ftp.server.request.FtpUserRequest;
import fr.univ.lille1.ftp.util.FtpConstants;

public class FtpServer {
	
	/**
	 * TODO : TYPE A => transfert suivant en texte & TYPE I => transfert suivant en binaire
	 */
		
	private ServerSocket serverSocket;
	
	private String ftpDirectory;

	
	private FtpServer(String ftpDirectory, int ftpPort) throws IOException {
		this.ftpDirectory = ftpDirectory;
		this.serverSocket = new ServerSocket(ftpPort);
	}
	
	public static Socket acceptConnection() throws IOException{
		Socket newSocket = serverSocket.accept();
		
		DataOutputStream br = new DataOutputStream(newSocket.getOutputStream());
		br.writeBytes("Welcome to my great FTP server\n");
		
		return newSocket;
	}
	
	public void close() {
		//TODO
	}
	
	public static void main(String[] args) {
		
		if(args.length == 0) {
			throw new IllegalArgumentException("Usage : todo");
		}
		
		
		FtpServer server = null;
		
		try {

			server = new FtpServer(args[0], FtpConstants.FTP_SERVER_PORT);
			while(true) {
				
				// Accept a new connection and say hello to our lucky user
				Socket newSocket = FtpServer.acceptConnection();
				
				server.handleInputRequest();
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
