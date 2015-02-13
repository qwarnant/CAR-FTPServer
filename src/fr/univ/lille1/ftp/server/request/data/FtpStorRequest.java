package fr.univ.lille1.ftp.server.request.data;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.Socket;

import fr.univ.lille1.ftp.server.request.FtpResponse;
import fr.univ.lille1.ftp.util.FtpConstants;
import fr.univ.lille1.ftp.util.FtpUtils;

/**
 * Created by Warnant on 10-02-15.
 */
public class FtpStorRequest extends FtpDataRequest {

    private String username;
    private String currentDirectory;

    public FtpStorRequest(String commandLine, String currentDirectory, String username, char currentType,
                          String remoteIp, int remotePort) {
        super(commandLine, currentType, remoteIp, remotePort);

        this.username = username;
        this.currentDirectory = currentDirectory;
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
            this.dataSocket = new Socket(remoteIp, remotePort);

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
