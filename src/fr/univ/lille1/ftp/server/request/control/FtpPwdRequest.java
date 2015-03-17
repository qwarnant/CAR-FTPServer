package fr.univ.lille1.ftp.server.request.control;

import java.io.IOException;

import fr.univ.lille1.ftp.server.request.FtpRequest;
import fr.univ.lille1.ftp.server.request.FtpResponse;
import fr.univ.lille1.ftp.util.FtpConstants;

/**
 * FtpPwdRequest is the class associated to the PWD ftp request
 * PWD allows the user client to print his current directory on the ftp server
 *
 * @author Quentin Warnant
 * @version 1.0
 */
public class FtpPwdRequest extends FtpRequest {

    private String currentDirectory;

    /**
     * Class constructor
     *
     * @param commandLine      String the request client command line
     * @param currentDirectory String the user current directory on the ftp server
     */
    public FtpPwdRequest(String commandLine, String currentDirectory) {
        super(commandLine);
        this.currentDirectory = currentDirectory;
    }

    @Override
    public FtpResponse process() throws IOException {
        return new FtpResponse(FtpConstants.FTP_REP_PWD_CODE,
                "\"" + this.currentDirectory + "\"");
    }

}
