package fr.univ.lille1.ftp.server.request.control;


public class FtpCdupRequest extends FtpCwdRequest {

	public FtpCdupRequest(String commandLine, String currentDirectory) {
		super(commandLine + " ..", currentDirectory);
	}
	


}
