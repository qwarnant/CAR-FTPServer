package fr.univ.lille1.ftp.server.request.control;

import java.io.IOException;

import fr.univ.lille1.ftp.server.request.FtpRequest;
import fr.univ.lille1.ftp.server.request.FtpResponse;
import fr.univ.lille1.ftp.util.FtpConstants;

public class FtpSystRequest extends FtpRequest {

	public FtpSystRequest(String commandLine) {
		super(commandLine);
	}

	@Override
	public FtpResponse process() throws IOException {
		//TODO
		return new FtpResponse(
				FtpConstants.FTP_REP_SYST_CODE, 
				""
				);
	}

}
