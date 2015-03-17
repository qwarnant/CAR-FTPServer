package fr.univ.lille1.ftp.server.request.control;

import java.io.IOException;

import fr.univ.lille1.ftp.server.FtpUserManager;
import fr.univ.lille1.ftp.server.request.FtpRequest;
import fr.univ.lille1.ftp.server.request.FtpResponse;
import fr.univ.lille1.ftp.util.FtpConstants;

/**
 * FtpPassRequest is the class associated to the PASS ftp request
 * PASS allows the user to send the password associated to his ftp account
 * on the server
 *
 * @author Quentin Warnant
 * @version 1.0
 */
public class FtpPassRequest extends FtpRequest {

    private String username;

    /**
     * Class constructor
     * @param commandLine String the request client command line
     * @param username String the current username
     */
    public FtpPassRequest(String commandLine, String username) {
        super(commandLine);
        this.username = username;
    }

    @Override
    public FtpResponse process() throws IOException {

        // Bad sequence of commands
        if (this.username == null) {
            return new FtpResponse(
                    FtpConstants.FTP_ERR_BAD_SEQ_CODE,
                    FtpConstants.FTP_ERR_BAD_SEQ_MSG);
        }

        // Get the user name
        String password = this.commandLine.substring(5, this.commandLine.length());

        if (!FtpUserManager.getInstance().checkPassword(username, password)) {
            return new FtpResponse(
                    FtpConstants.FTP_ERR_INVALID_USER_PWD_CODE,
                    FtpConstants.FTP_ERR_INVALID_USER_PWD_MSG);
        }

        if (this.username.equals(FtpConstants.FTP_ANONYMOUS_NAME)) {
            return new FtpResponse(
                    FtpConstants.FTP_REP_LOGIN_OK_CODE,
                    FtpConstants.FTP_REP_ANONYM_LOGIN_OK_MSG);
        }

        return new FtpResponse(FtpConstants.FTP_REP_LOGIN_OK_CODE,
                FtpConstants.FTP_REP_LOGIN_OK_MSG);
    }

}
