package fr.univ.lille1.ftp.server.request;

import java.io.IOException;

import fr.univ.lille1.ftp.server.FtpUserManager;
import fr.univ.lille1.ftp.util.FtpConstants;

public class FtpPassRequest extends FtpRequest {

    private String username;

    public FtpPassRequest(String commandLine, String username) {
        super(commandLine);
        this.username = username;
    }

    @Override
    public FtpResponse process() throws IOException {

        if (this.username == null) {
            //TODO
        }

        // Get the user name
        String password = this.commandLine.substring(5, this.commandLine.length());

        if (!FtpUserManager.getInstance().checkPassword(username, password)) {
            return new FtpResponse(
                    FtpConstants.FTP_ERROR_INVALID_USER_PWD_CODE,
                    FtpConstants.FTP_ERROR_INVALID_USER_PWD_MSG);
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
