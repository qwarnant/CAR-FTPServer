package fr.univ.lille1.ftp.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import fr.univ.lille1.ftp.util.FtpConstants;

/**
 * FtpServer is the class that handles the main server socket
 *
 * @author Quentin Warnant
 * @version 1.0
 */
public class FtpServer {

    private ServerSocket serverSocket;
    private static String ftpDirectory;

    /**
     * Class constructor
     *
     * @param ftpDirectory String the default directory of the ftp
     * @param ftpPort      int the default ftp server port
     * @throws IOException
     */
    public FtpServer(String ftpDirectory, int ftpPort) throws IOException {
        this.ftpDirectory = ftpDirectory;
        this.serverSocket = new ServerSocket(ftpPort);
    }

    /**
     * This method closes the main server socket of the current instance
     */
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

    /**
     * This method is waiting a new ftp input request from a client
     * to create a new socket
     *
     * @return Socket the new socket for the new connection
     * @throws IOException
     */
    public Socket accept() throws IOException {
        if (this.serverSocket == null)
            return null;
        return this.serverSocket.accept();
    }

    /**
     * This method returns the default ftp directory
     *
     * @return String the default directory
     */
    public static String getFtpDirectory() {
        return ftpDirectory;
    }

}
