package fr.univ.lille1.ftp.main;

import java.io.IOException;
import java.net.Socket;

import fr.univ.lille1.ftp.server.FtpServer;
import fr.univ.lille1.ftp.server.FtpThread;
import fr.univ.lille1.ftp.util.FtpConstants;

public class FTPServerMain {

	public static void main(String[] args) {

		if (args.length == 0) {
			throw new IllegalArgumentException("Usage : todo");
		}

		FtpServer server = null;

		try {

			server = new FtpServer(args[0], FtpConstants.FTP_SERVER_PORT);
			while (true) {

				// Accept a new connection
				Socket newSocket = server.accept();

				// Custom thread for our new client
				new FtpThread(newSocket).run();
			}

		} catch (IOException e) {
			if (FtpConstants.DEBUG_ENABLED) {
				System.out.println(e.getMessage());
			}
		} finally {
			if (server != null) {
				server.close();
			}
		}

	}

}
