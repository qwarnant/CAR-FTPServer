package fr.univ.lille1.ftp.server.request.control;

import java.io.File;
import java.io.IOException;

import fr.univ.lille1.ftp.server.request.FtpRequest;
import fr.univ.lille1.ftp.server.request.FtpResponse;
import fr.univ.lille1.ftp.util.FtpConstants;

public class FtpMkdRequest extends FtpRequest {

	public FtpMkdRequest(String commandLine) {
		super(commandLine);
		// TODO Auto-generated constructor stub
	}

	@Override
	public FtpResponse process() throws IOException {
		// Get the target directory
		String targetDirectoryPath = this.commandLine.substring(5,
				commandLine.length());
		
		String fullDirectoryPath = null;
		

		File targetDirectory = new File(fullDirectoryPath);
		// Check directory exists
		if (targetDirectory == null || targetDirectory.exists()) {
			return new FtpResponse(FtpConstants.FTP_ERR_DIR_EXISTS_CODE,
					"'" + fullDirectoryPath + "' "
							+ FtpConstants.FTP_ERR_DIR_EXISTS_MSG);
		}

		// TODO Auto-generated method stub
		
			
		return new FtpResponse(FtpConstants.FTP_REP_PWD_CODE,
				targetDirectoryPath + " " + FtpConstants.FTP_REP_MKD_MSG);
	}

}
