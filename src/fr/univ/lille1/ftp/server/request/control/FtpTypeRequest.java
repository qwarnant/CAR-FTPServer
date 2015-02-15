package fr.univ.lille1.ftp.server.request.control;

import java.io.IOException;

import fr.univ.lille1.ftp.server.request.FtpRequest;
import fr.univ.lille1.ftp.server.request.FtpResponse;
import fr.univ.lille1.ftp.util.FtpConstants;

/**
 * FtpTypeRequest is the class associated to the TYPE ftp request
 * TYPE allows the user to change the data transfer mode between
 * ASCII (A) or BINARY (I)
 *
 * @author Quentin Warnant
 * @version 1.0
 */
public class FtpTypeRequest extends FtpRequest {

    private char type;

    /**
     * Class constructor
     *
     * @param commandLine String the request client command line
     */
    public FtpTypeRequest(String commandLine) {
        super(commandLine);
    }

    @Override
    public FtpResponse process() throws IOException {

        // Get the request type
        this.type = this.commandLine.substring(4, this.commandLine.length()).trim().charAt(0);

        String typeResponse = String.format(FtpConstants.FTP_REP_TYPE_CMD_MSG, "" + this.type);
        return new FtpResponse(FtpConstants.FTP_REP_CMD_OK_CODE, typeResponse);
    }

    /**
     * This method returns the new data transfer type character
     * for the current connection
     *
     * @return char A for ASCII mode or I for binary mode
     */
    public char getType() {
        return this.type;
    }
}
