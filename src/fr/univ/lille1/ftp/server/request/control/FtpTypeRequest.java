package fr.univ.lille1.ftp.server.request.control;

import java.io.IOException;

import fr.univ.lille1.ftp.server.request.FtpRequest;
import fr.univ.lille1.ftp.server.request.FtpResponse;
import fr.univ.lille1.ftp.util.FtpConstants;

/**
 * Created by Warnant on 09-02-15.
 */
public class FtpTypeRequest extends FtpRequest {

    private char type;

    public FtpTypeRequest(String commandLine) {
        super(commandLine);
    }

    @Override
    public FtpResponse process() throws IOException {

        // Get the request type
        this.type = this.commandLine.substring(5, 1).charAt(0);

        String typeResponse = String.format(FtpConstants.FTP_REP_TYPE_CMD_MSG, "" + this.type);
        return new FtpResponse(FtpConstants.FTP_REP_CMD_OK_CODE, typeResponse);
    }

    public char getType() {
        return this.type;
    }
}
