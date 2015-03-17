package fr.univ.lille1.ftp.server.request.control;

import java.io.File;
import java.io.IOException;

import fr.univ.lille1.ftp.server.FtpServer;
import fr.univ.lille1.ftp.server.request.FtpRequest;
import fr.univ.lille1.ftp.server.request.FtpResponse;
import fr.univ.lille1.ftp.util.FtpConstants;
import fr.univ.lille1.ftp.util.FtpPath;
import fr.univ.lille1.ftp.util.FtpUtils;

/**
 * FtpCwdRequest is the class associated to the CDW ftp request
 * CDW allows the user to change his current directory on the ftp server
 *
 * @author Quentin Warnant
 * @version 1.0
 */


public class FtpCwdRequest extends FtpRequest {

    private String currentDirectory;
    private String newCurrentDictory;

    /**
     * Class constructor
     *
     * @param commandLine      String the request client command line
     * @param currentDirectory String the user current directory on the ftp server
     */
    public FtpCwdRequest(String commandLine, String currentDirectory) {
        super(commandLine);
        this.currentDirectory = currentDirectory;
    }

    @Override
    public FtpResponse process() throws IOException {

        FtpPath targetDirectoryPath = FtpUtils.extractArgumentFromCommandLine(this.commandLine, this.currentDirectory);

        File targetDirectory = new File(targetDirectoryPath.getPath());
        // Check directory exists
        if (targetDirectory == null || !targetDirectory.exists()
                && !targetDirectory.isDirectory()) {
            this.newCurrentDictory = this.currentDirectory;
            return new FtpResponse(FtpConstants.FTP_ERR_ACTION_NOT_TAKEN,
                    "'" + targetDirectoryPath + "' : "
                            + FtpConstants.FTP_ERR_FILE_NO_EXISTS_MSG);
        }

        // Check if the directory is accessible
        if(!targetDirectoryPath.getPath().contains(FtpServer.getFtpDirectory())) {
            this.newCurrentDictory = this.currentDirectory;
            return new FtpResponse(
                    FtpConstants.FTP_ERR_ACTION_NOT_TAKEN,
                    FtpConstants.FTP_ERR_ACCESS_DENIED_MSG
            );
        }

        this.newCurrentDictory = targetDirectoryPath.getPath();

        return new FtpResponse(FtpConstants.FTP_REP_CMD_OK_CODE,
                FtpConstants.FTP_CMD_CWD + " " + FtpConstants.FTP_REP_CMD_MSG);
    }

    /**
     * This method returns the new current directory after the CWD command
     *
     * @return String the new path of the current directory
     */
    public String getNewCurrentDirectory() {
        return this.newCurrentDictory;
    }

}
