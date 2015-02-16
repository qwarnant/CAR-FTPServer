package fr.univ.lille1.ftp.main;

import java.io.IOException;
import java.net.Socket;

import fr.univ.lille1.ftp.server.FtpServer;
import fr.univ.lille1.ftp.server.FtpThread;
import fr.univ.lille1.ftp.util.FtpConstants;

/**
 * FtpServerMain is the executable class with initiate the main ftp
 * server object and waits for a new incoming connection
 *
 * @author Quentin Warnant
 * @version 1.0
 */
public class FTPServerMain {

    /**
     * Main
     *
     * @param args String[] string[0] is the main ftp directory and String[1] is the path of the ftp log file
     */
    public static void main(String[] args) {

        if (args.length != 2) {
            throw new IllegalArgumentException("Usage : FTPServermain ftpMainDirectory ftpLogFile");
        }

        FtpServer server = null;

        try {

            server = new FtpServer(args[0], args[1], FtpConstants.FTP_SERVER_PORT);
            while (true) {

                // Accept a new connection
                Socket newSocket = server.accept();

                // Custom thread for our new client
                new FtpThread(newSocket).run();
            }

        } catch (IOException e) {
            FtpServer.getFtpLogger().error(e.getMessage());
        } finally {
            if (server != null) {
                server.close();
            }
        }

    }

}
