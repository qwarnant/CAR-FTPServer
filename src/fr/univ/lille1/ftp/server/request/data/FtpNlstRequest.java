package fr.univ.lille1.ftp.server.request.data;

import fr.univ.lille1.ftp.server.FtpServer;
import fr.univ.lille1.ftp.server.request.FtpResponse;
import fr.univ.lille1.ftp.util.FtpConstants;
import fr.univ.lille1.ftp.util.FtpPath;
import fr.univ.lille1.ftp.util.FtpUtils;

import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.Socket;

/**
 * FtpNlstRequest is the ftp request that allows the user
 * to list a directory on the ftp server
 * Similar to LIST request with less information available for the files
 *
 * @author Quentin Warnant
 * @version 1.0
 */
public class FtpNlstRequest extends FtpDataRequest {

    private FtpPath targetDirectoryPath;

    /**
     * Class constructor
     *
     * @param commandLine      String the input client command line
     * @param currentDirectory String the current ftp server directory
     * @param currentType      char the current transfer mode type
     * @param socket           Socket the current data socket connection
     */
    public FtpNlstRequest(String commandLine, String currentDirectory, char currentType,
                          Socket socket) {
        super(commandLine, currentDirectory, currentType, socket);
    }

    @Override
    public FtpResponse process() throws IOException {

        // Check if the data connection has been established before the processing
        if(this.dataSocket == null) {
            return new FtpResponse(FtpConstants.FTP_ERR_CONNECTION_FAILED_CODE,
                    FtpConstants.FTP_ERR_CONNECTION_FAILED_MSG);
        }

        File targetDirectory = new File(this.targetDirectoryPath.getPath());
        // Check directory exists
        if (targetDirectory == null || !targetDirectory.exists()
                && !targetDirectory.isDirectory()) {
            return new FtpResponse(FtpConstants.FTP_ERR_ACTION_NOT_TAKEN,
                    "'" + targetDirectoryPath + "' : "
                            + FtpConstants.FTP_ERR_FILE_NO_EXISTS_MSG);
        }

        // Write the file list on the socket
        String fileList = FtpUtils.listFilesInFolder(targetDirectory, false, false);
        FtpServer.getFtpLogger().info(fileList);

        this.out.writeBytes(fileList);

        // Close the data socket
        this.out.close();
        this.dataSocket.close();

        return new FtpResponse(FtpConstants.FTP_REP_TRANSF_COMPL_CODE,
                FtpConstants.FTP_REP_TRANSF_COMPL_MSG);
    }

    @Override
    public FtpResponse prepare() {
        try {

            // Check if the data socket has been opened
            if(this.dataSocket == null) {
                return new FtpResponse(FtpConstants.FTP_ERR_CONNECTION_FAILED_CODE,
                        FtpConstants.FTP_ERR_CONNECTION_FAILED_MSG);
            }

            this.out = new DataOutputStream(dataSocket.getOutputStream());
            this.targetDirectoryPath = FtpUtils.extractArgumentFromCommandLine(this.commandLine, this.currentDirectory);

            String responseString = String.format(
                    FtpConstants.FTP_REP_DATA_OK_MSG,
                    FtpUtils.getTransferTypeString(currentType),
                    this.targetDirectoryPath);

            return new FtpResponse(FtpConstants.FTP_REP_DATA_OK_CODE,
                    responseString);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return new FtpResponse(FtpConstants.FTP_ERR_CONNECTION_FAILED_CODE,
                FtpConstants.FTP_ERR_CONNECTION_FAILED_MSG);
    }
}
