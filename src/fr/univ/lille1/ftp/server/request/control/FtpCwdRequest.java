package fr.univ.lille1.ftp.server.request.control;

import java.io.File;
import java.io.IOException;

import fr.univ.lille1.ftp.server.request.FtpRequest;
import fr.univ.lille1.ftp.server.request.FtpResponse;
import fr.univ.lille1.ftp.util.FtpConstants;

public class FtpCwdRequest extends FtpRequest {

	private String currentDirectory;
	private String newCurrentDictory;

	public FtpCwdRequest(String commandLine, String currentDirectory) {
		super(commandLine);
		this.currentDirectory = currentDirectory;
	}

	@Override
	public FtpResponse process() throws IOException {

		// Get the target directory
		String targetDirectoryPath = (this.commandLine.length() > 4) ? this.commandLine.substring(4,
				commandLine.length()) : "";
		String fullDirectoryPath = this.currentDirectory + "/" + targetDirectoryPath;

		File targetDirectory = new File(fullDirectoryPath);
		// Check directory exists
		if (targetDirectory == null || !targetDirectory.exists()
				&& !targetDirectory.isDirectory()) {
			return new FtpResponse(FtpConstants.FTP_ERR_ACTION_NOT_TAKEN,
					"'" + fullDirectoryPath + "' : "
							+ FtpConstants.FTP_ERR_FILE_NO_EXISTS_MSG);
		}

		this.currentDirectory = fullDirectoryPath;

		return new FtpResponse(FtpConstants.FTP_REP_CMD_OK_CODE,
				FtpConstants.FTP_CMD_CWD + " " + FtpConstants.FTP_REP_CMD_MSG);
	}

	public String getNewCurrentDirectory() {
		return this.newCurrentDictory;
	}

}
