package fr.univ.lille1.ftp.client;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.UnknownHostException;

import fr.univ.lille1.ftp.util.FtpConstants;

public class FtpClient {

	private Socket clientSocket;
	private String username;
	private String password;

	public FtpClient(Socket socket, String username, String password) {
		this.clientSocket = socket;
		this.username = username;
		this.password = password;
	}

	public void connect(SocketAddress sa) throws IOException {
		this.clientSocket.connect(sa);
	}

	public void dummyRead() throws IOException {
		InputStreamReader is = new InputStreamReader(
				this.clientSocket.getInputStream());
		BufferedReader br = new BufferedReader(is);
		System.out.println(br.readLine());
	}
	
	public void doUserRequest() throws IOException {
		DataOutputStream dos = new DataOutputStream(this.clientSocket.getOutputStream());
		dos.writeBytes("USER " + username);
	}

	public static void main(String[] args) {
		
		if(args.length != 2) {
			throw new IllegalArgumentException("Usage : FtpClient username password");
		}
		
		String username = args[0];
		String password = args[1];

		try {

			FtpClient client = new FtpClient(new Socket(), username, password);
			SocketAddress sa = new InetSocketAddress(
					InetAddress.getByName(FtpConstants.FTP_HOST_NAME),
					FtpConstants.FTP_SERVER_PORT);
			client.connect(sa);
			client.dummyRead();
			client.doUserRequest();

		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
