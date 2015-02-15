package fr.univ.lille1.ftp.server.request.data;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import fr.univ.lille1.ftp.server.request.FtpRequest;
import fr.univ.lille1.ftp.server.request.FtpResponse;

/**
 * FtpDataRequest is the class that handles all the ftp transfer data requests
 * with a custom data socket for the transfer
 * <p/>
 * Includes NLST (LIST), RETR and STOR requests
 *
 * @author Quentin Warnant
 * @version 1.0
 */
public abstract class FtpDataRequest extends FtpRequest {

    protected char currentType;
    protected Socket dataSocket;
    protected String currentDirectory;

    protected DataInputStream in;
    protected DataOutputStream out;

    /**
     * Class constructor
     *
     * @param commandLine      String the input client command line
     * @param currentDirectory String the current ftp server directory
     * @param currentType      char the current transfer mode type
     * @param socket           Socket the current data socket connection
     */
    public FtpDataRequest(String commandLine, String currentDirectory, char currentType,
                          Socket socket) {
        super(commandLine);
        this.dataSocket = socket;
        this.currentType = currentType;
        this.currentDirectory = currentDirectory;
    }

    /**
     * This method prepare the data connection for the transfer
     * with the appropriate header written on the socket connection
     *
     * @return FtpResponse the ftp response header which shows that the server is ready
     */
    public abstract FtpResponse prepare();

}
