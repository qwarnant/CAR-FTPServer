package fr.univ.lille1.ftp.server.request.data;

import fr.univ.lille1.ftp.server.request.FtpResponse;
import fr.univ.lille1.ftp.util.FtpConstants;
import fr.univ.lille1.ftp.util.FtpUtils;

import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

/**
 * Created by Warnant on 10-02-15.
 */
public class FtpRetrRequest extends FtpDataRequest {

    private String username;
    private String currentDirectory;

    public FtpRetrRequest(String commandLine, String currentDirectory, String username, char currentType,
                          String remoteIp, int remotePort) {
        super(commandLine, currentType, remoteIp, remotePort);

        this.username = username;
        this.currentDirectory = currentDirectory;
    }

    @Override
    public FtpResponse process() throws IOException {

        String targetFilePath = this.commandLine.substring(5,
                commandLine.length());

        if (targetFilePath.length() == 0) {
            return new FtpResponse(
                    FtpConstants.FTP_ERR_SYNTAX_CODE,
                    FtpConstants.FTP_ERR_SYNTAX_MSG
            );
        }

        // Copy the file into the output stream
        File targetFile = new File(this.currentDirectory + targetFilePath);
        Files.copy(targetFile.toPath(), this.dataSocket.getOutputStream());

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
