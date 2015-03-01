package fr.univ.lille1.ftp.server.request.control;

import java.io.IOException;

import fr.univ.lille1.ftp.server.request.FtpRequest;
import fr.univ.lille1.ftp.server.request.FtpResponse;
import fr.univ.lille1.ftp.util.FtpConstants;

/**
 * FtpSystRequest is the class associated to the SYST ftp request
 * SYST allows the user to delete a file on the ftp server
 *
 * @author Quentin Warnant
 * @version 1.0
 */
public class FtpSystRequest extends FtpRequest {

    /**
     * Class constructor
     * @param commandLine String the request client command line
     */
	public FtpSystRequest(String commandLine) {
		super(commandLine);
	}

	@Override
	public FtpResponse process() throws IOException {
		return new FtpResponse(
				FtpConstants.FTP_REP_SYST_CODE, 
				FtpConstants.FTP_REP_SYST_MSG
				);
	}

}
