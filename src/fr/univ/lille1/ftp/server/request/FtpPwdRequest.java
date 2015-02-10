package fr.univ.lille1.ftp.server.request;

import java.io.IOException;

import fr.univ.lille1.ftp.util.FtpConstants;

public class FtpPwdRequest extends FtpRequest {

	private String currentDirectory;

	public FtpPwdRequest(String commandLine, String currentDirectory) {
		super(commandLine);
		this.currentDirectory = currentDirectory;
	}

	@Override
	public FtpResponse process() throws IOException {
		return new FtpResponse(FtpConstants.FTP_REP_PWD_CODE,
				this.currentDirectory + " " + FtpConstants.FTP_REP_PWD_CODE);
	}

}
