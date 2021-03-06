package fr.univ.lille1.ftp.server.request.control;

import fr.univ.lille1.ftp.server.FtpPortManager;
import fr.univ.lille1.ftp.server.request.FtpRequest;
import fr.univ.lille1.ftp.server.request.FtpResponse;
import fr.univ.lille1.ftp.util.FtpConstants;

import java.io.IOException;

/**
 * FtpPasvRequest is the class associated to the PASV ftp request
 * PASV allows the user client to put the connection into passive mode
 * In the passive mode, the client initiates the data connection to the
 * server
 *
 * @author Quentin Warnant
 * @version 1.0
 */
public class FtpPasvRequest extends FtpRequest {

    private String localIp;
    private int localPort;

    /**
     * Class constructor
     *
     * @param commandLine String the request client command line
     */
    public FtpPasvRequest(String commandLine) {
        super(commandLine);
    }

    @Override
    public FtpResponse process() throws IOException {

        // Get a free number port
        int portNumber = FtpPortManager.getInstance().getNewPort();

        this.localIp = FtpConstants.FTP_HOST_NAME;
        this.localPort = portNumber;

        int portNumber1 = portNumber / 256;
        int portNumber2 = portNumber % 256;

        String[] localIp = FtpConstants.FTP_HOST_NAME.split("\\.");

        // Parse the pasv data response
        String PasvFormat = String.format(
                FtpConstants.FTP_REP_PASV_MODE_MSG,
                Integer.parseInt(localIp[0]),
                Integer.parseInt(localIp[1]),
                Integer.parseInt(localIp[2]),
                Integer.parseInt(localIp[3]),
                portNumber1,
                portNumber2
        );

        return new FtpResponse(FtpConstants.FTP_REP_PASV_MODE_CODE, PasvFormat);
    }

    /**
     * This method returns the current local IP address used for the data connection
     *
     * @return String the local IP address
     */
    public String getLocalIp() {
        return this.localIp;
    }

    /**
     * This method returns the current local port number used for the data connection
     *
     * @return int the local port number
     */
    public int getLocalPort() {
        return this.localPort;
    }
}
