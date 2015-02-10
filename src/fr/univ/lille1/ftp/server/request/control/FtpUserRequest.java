package fr.univ.lille1.ftp.server.request.control;

import fr.univ.lille1.ftp.server.FtpUserManager;
import fr.univ.lille1.ftp.server.request.FtpRequest;
import fr.univ.lille1.ftp.server.request.FtpResponse;
import fr.univ.lille1.ftp.util.FtpConstants;

import java.io.IOException;

public class FtpUserRequest extends FtpRequest {

    private String username;

    public FtpUserRequest(String commandLine) {
        super(commandLine);
    }

    @Override
    public FtpResponse process() throws IOException {
        // Get the user name
        String username = this.commandLine.substring(5, commandLine.length());

        if (!FtpUserManager.getInstance().containsUser(username)) {
            return new FtpResponse(
                    FtpConstants.FTP_ERROR_INVALID_USER_PWD_CODE,
                    FtpConstants.FTP_ERROR_INVALID_USER_PWD_MSG);
        }

        this.username = username;

        // Anonymous connection
        if (this.username.equals(FtpConstants.FTP_ANONYMOUS_NAME)) {
            return new FtpResponse(
                    FtpConstants.FTP_REP_NEED_USER_CODE,
                    FtpConstants.FTP_REP_ANONYM_PASS_MSG);
        }

        return new FtpResponse(FtpConstants.FTP_REP_NEED_USER_CODE,
                FtpConstants.FTP_REP_NEED_USER_MSG);

    }

    public String getResultUsername() {
        return this.username;
    }

}
