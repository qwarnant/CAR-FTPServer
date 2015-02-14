package fr.univ.lille1.ftp.server.request.control;

import java.io.IOException;

import fr.univ.lille1.ftp.server.request.FtpRequest;
import fr.univ.lille1.ftp.server.request.FtpResponse;

public class FtpRmdRequest extends FtpRequest {

    private String username;
    private String currentDirectory;

    public FtpRmdRequest(String commandLine, String currentDirectory, String username) {
        super(commandLine);
        this.username = username;
        this.currentDirectory = currentDirectory;
    }


	@Override
	public FtpResponse process() throws IOException {
		// TODO Auto-generated method stub
		return null;
	}

}
