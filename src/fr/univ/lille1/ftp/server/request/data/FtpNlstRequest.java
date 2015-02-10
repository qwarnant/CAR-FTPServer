package fr.univ.lille1.ftp.server.request.data;

import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.Socket;

import fr.univ.lille1.ftp.server.FtpServer;
import fr.univ.lille1.ftp.server.request.FtpResponse;
import fr.univ.lille1.ftp.util.FtpConstants;
import fr.univ.lille1.ftp.util.FtpUtils;

/**
 * Created by Warnant on 06-02-15.
 */
public class FtpNlstRequest extends FtpDataRequest {

	public FtpNlstRequest(String commandLine, char currentType,
			String remoteIp, int remotePort) {
		super(commandLine, currentType, remoteIp, remotePort);
	}

	@Override
	public FtpResponse process() throws IOException {
		String targetDirectoryPath = this.commandLine.substring(5,
				commandLine.length());

		// If there is now argument directory, get the default
		if (targetDirectoryPath.length() == 0) {
			targetDirectoryPath = FtpServer.getFtpDirectory();
		}

		File targetDirectory = new File(targetDirectoryPath);
		// Check directory exists
		if (targetDirectory == null || !targetDirectory.exists()
				&& !targetDirectory.isDirectory()) {
			return new FtpResponse(FtpConstants.FTP_ERR_ACTION_NOT_TAKEN,
					"'" + targetDirectoryPath + "' : "
							+ FtpConstants.FTP_ERR_FILE_NO_EXISTS_MSG);
		}

		// Write the file list on the socket
		String fileList = FtpUtils.listFilesInFolder(targetDirectory);
		this.out.writeBytes(fileList);

		// Close the data socket
		this.out.close();
		this.dataSocket.close();

		return new FtpResponse(FtpConstants.FTP_REP_TRANSF_COMPL_CODE,
				FtpConstants.FTP_REP_TRANSF_COMPL_MSG);
	}

	@Override
	public FtpResponse prepare() {
		try {
			this.dataSocket = new Socket(remoteIp, remotePort);
			
			// TODO handle the TYPE format
			
			this.out = new DataOutputStream(dataSocket.getOutputStream());

			String responseString = String.format(
					FtpConstants.FTP_REP_DATA_OK_MSG,
					FtpUtils.getTransferTypeString(currentType),
					this.commandLine.substring(5, commandLine.length()));

			return new FtpResponse(FtpConstants.FTP_REP_DATA_OK_CODE,
					responseString);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return new FtpResponse(FtpConstants.FTP_ERR_CONNECTION_FAILED_CODE,
				FtpConstants.FTP_ERR_CONNECTION_FAILED_MSG);
	}
}