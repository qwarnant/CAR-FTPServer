package fr.univ.lille1.ftp.server.request.control;

import fr.univ.lille1.ftp.server.request.FtpRequest;
import fr.univ.lille1.ftp.server.request.FtpResponse;
import fr.univ.lille1.ftp.util.FtpConstants;

import java.io.IOException;

/**
 * FtpPortRequest is the class associated to the PASV ftp request
 * PASV allows the user client to put the connection into passive mode
 * In the passive mode, the client initiates the data connection to the
 * server
 *
 * @author Quentin Warnant
 * @version 1.0
 */
public class FtpPortRequest extends FtpRequest {

    private String remoteIp;
    private int remotePort;

    /**
     * Class constructor
     *
     * @param commandLine String the request client command line
     */
    public FtpPortRequest(String commandLine) {
        super(commandLine);
    }

    public FtpResponse process() throws IOException {
        String portCommand = this.commandLine.substring(5, commandLine.length());
        String[] portCommandParts = portCommand.split(",");

        // Syntax error
        if (portCommandParts.length != 6) {
            return new FtpResponse(
                    FtpConstants.FTP_ERR_SYNTAX_CODE,
                    FtpConstants.FTP_ERR_SYNTAX_MSG);
        }

        // Extract the remote ip and the remote port from the line
        this.remoteIp = portCommandParts[0] + "." + portCommandParts[1] + "." + portCommandParts[2] + "." + portCommandParts[3];
        this.remotePort = Integer.parseInt(portCommandParts[4]) * 256 + Integer.parseInt(portCommandParts[5]);

        return new FtpResponse(
                FtpConstants.FTP_REP_CMD_OK_CODE,
                FtpConstants.FTP_CMD_PORT + " " + FtpConstants.FTP_REP_CMD_MSG);
    }

    /**
     * This method returns the current remote port number used for the data connection
     * on the ftp client
     *
     * @return int the remote port number
     */
    public int getRemotePort() {
        return this.remotePort;
    }

    /**
     * This method returns the current remote IP address used for the data connection
     * on the ftp client
     *
     * @return String the remote IP address
     */
    public String getRemoteIp() {
        return this.remoteIp;
    }
}
