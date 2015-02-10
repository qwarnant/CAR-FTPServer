package fr.univ.lille1.ftp.server.request.data;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

import fr.univ.lille1.ftp.server.request.FtpRequest;
import fr.univ.lille1.ftp.server.request.FtpResponse;

public abstract class FtpDataRequest extends FtpRequest {

	protected String remoteIp;
	protected int remotePort;
	protected char currentType;
	protected Socket dataSocket;

    protected DataInputStream in;
	protected DataOutputStream out;

	public FtpDataRequest(String commandLine, char currentType,
			String remoteIp, int remotePort) {
		super(commandLine);
		this.remoteIp = remoteIp;
		this.remotePort = remotePort;
		this.currentType = currentType;
	}

	public abstract FtpResponse prepare();

}
