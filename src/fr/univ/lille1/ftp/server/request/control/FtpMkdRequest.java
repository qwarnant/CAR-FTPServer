package fr.univ.lille1.ftp.server.request.control;

import java.io.File;
import java.io.IOException;

import fr.univ.lille1.ftp.server.request.FtpRequest;
import fr.univ.lille1.ftp.server.request.FtpResponse;
import fr.univ.lille1.ftp.util.FtpConstants;
import fr.univ.lille1.ftp.util.FtpPath;
import fr.univ.lille1.ftp.util.FtpUtils;

/**
 * FtpMkdRequest is the class associated to the (X)MKD ftp request
 * (X)MKD allows the user to create a new directory on the ftp server
 *
 * @author Quentin Warnant
 * @version 1.0
 */
public class FtpMkdRequest extends FtpRequest {

    private String username;
    private String currentDirectory;

    /**
     * Class constructor
     * @param commandLine String the request client command line
     * @param currentDirectory String the user current directory on the ftp server
     * @param username String the current username
     */
	public FtpMkdRequest(String commandLine, String currentDirectory, String username) {
		super(commandLine);
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

		// Get the target directory
		FtpPath targetDirectoryPath =FtpUtils.extractArgumentFromCommandLine(this.commandLine, this.currentDirectory);

		File targetDirectory = new File(targetDirectoryPath.getPath());
		// Check directory exists
		if (targetDirectory == null || targetDirectory.exists()) {
			return new FtpResponse(FtpConstants.FTP_ERR_DIR_EXISTS_CODE,
					"'" + targetDirectoryPath + "' "
							+ FtpConstants.FTP_ERR_DIR_EXISTS_MSG);
		}

        targetDirectory.mkdir();
			
		return new FtpResponse(FtpConstants.FTP_REP_PWD_CODE,
				targetDirectoryPath + " " + FtpConstants.FTP_REP_MKD_MSG);
	}

}
