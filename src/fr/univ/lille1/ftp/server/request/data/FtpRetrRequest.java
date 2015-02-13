package fr.univ.lille1.ftp.server.request.data;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import fr.univ.lille1.ftp.server.request.FtpResponse;
import fr.univ.lille1.ftp.util.FtpConstants;
import fr.univ.lille1.ftp.util.FtpUtils;

/**
 * Created by Warnant on 10-02-15.
 */
public class FtpRetrRequest extends FtpDataRequest {

    private String currentDirectory;

    public FtpRetrRequest(String commandLine, String currentDirectory,char currentType,
                          String remoteIp, int remotePort) {
        super(commandLine, currentType, remoteIp, remotePort);

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
        FtpUtils.copyStream(new FileInputStream(targetFile), this.dataSocket.getOutputStream());

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
