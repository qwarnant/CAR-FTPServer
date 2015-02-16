package fr.univ.lille1.ftp.server.request.data;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.Socket;

import fr.univ.lille1.ftp.server.request.FtpResponse;
import fr.univ.lille1.ftp.util.FtpConstants;
import fr.univ.lille1.ftp.util.FtpUtils;

/**
 * FtpStorRequest is the ftp request that allows the user
 * to put a file from local client space to the ftp server
 *
 * @author Quentin Warnant
 * @version 1.0
 */
public class FtpStorRequest extends FtpDataRequest {

    private String username;

    /**
     * Class constructor
     *
     * @param commandLine      String the input client command line
     * @param currentDirectory String the current ftp server directory
     * @param currentType      char the current transfer mode type
     * @param socket           Socket the current data socket connection
     * @param username         String the current ftp client user name
     */
    public FtpStorRequest(String commandLine, String currentDirectory, String username, char currentType,
                          Socket socket) {
        super(commandLine, currentDirectory, currentType, socket);

        this.username = username;
    }

    @Override
    public FtpResponse process() throws IOException {

        // Anonymous have no write access ?
        if (!FtpConstants.CAN_ANONYNOUS_WRITE &&
                this.username.equals(FtpConstants.FTP_ANONYMOUS_NAME)) {
            return new FtpResponse(
                    FtpConstants.FTP_ERR_ACTION_NOT_TAKEN,
                    FtpConstants.FTP_ERR_ACCESS_DENIED_MSG
            );
        }

        String targetFilePath = this.commandLine.substring(5,
                commandLine.length());

        if (targetFilePath.length() == 0) {
            return new FtpResponse(
                    FtpConstants.FTP_ERR_SYNTAX_CODE,
                    FtpConstants.FTP_ERR_SYNTAX_MSG
            );
        }

        // Copy the input stream into the new file
        File targetFile = new File(this.currentDirectory + targetFilePath);
        FtpUtils.copyStream(this.dataSocket.getInputStream(), new FileOutputStream(targetFile));

        // Close the data socket
        this.dataSocket.close();

        return new FtpResponse(FtpConstants.FTP_REP_TRANSF_COMPL_CODE,
                FtpConstants.FTP_REP_TRANSF_COMPL_MSG);
    }

    @Override
    public FtpResponse prepare() {
        try {
            String responseString = String.format(
                    FtpConstants.FTP_REP_DATA_OK_MSG,
                    FtpUtils.getTransferTypeString(currentType),
                    this.commandLine.substring(5, commandLine.length()));

            return new FtpResponse(FtpConstants.FTP_REP_DATA_OK_CODE,
                    responseString);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return new FtpResponse(FtpConstants.FTP_ERR_CONNECTION_FAILED_CODE,
                FtpConstants.FTP_ERR_CONNECTION_FAILED_MSG);
    }
}
