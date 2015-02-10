package fr.univ.lille1.ftp.server.request;

import java.io.IOException;

public abstract class FtpRequest {

	protected String commandLine;

	public FtpRequest(String commandLine) {
		this.commandLine = commandLine;
	}

	public abstract FtpResponse process() throws IOException;
}
