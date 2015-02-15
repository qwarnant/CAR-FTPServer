package fr.univ.lille1.ftp.server.request.data;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import fr.univ.lille1.ftp.server.request.FtpRequest;
import fr.univ.lille1.ftp.server.request.FtpResponse;

public abstract class FtpDataRequest extends FtpRequest {

	protected char currentType;
	protected Socket dataSocket;
    protected String currentDirectory;

    protected DataInputStream in;
	protected DataOutputStream out;

	public FtpDataRequest(String commandLine, String currentDirectory, char currentType,
			Socket socket) {
		super(commandLine);
		this.dataSocket = socket;
		this.currentType = currentType;
        this.currentDirectory = currentDirectory;
	}

	public abstract FtpResponse prepare();

}
