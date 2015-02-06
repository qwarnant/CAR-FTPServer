package fr.univ.lille1.ftp.server.request;

import fr.univ.lille1.ftp.util.FtpConstants;

import java.io.IOException;

/**
 * Created by Warnant on 06-02-15.
 */
public class FtpPortRequest extends FtpRequest {

    private String remoteIp;
    private int remotePort;

    public FtpPortRequest(String commandLine) {
        super(commandLine);
    }

    public FtpResponse process() throws IOException {
        String portCommand = this.commandLine.substring(5, commandLine.length());
        String[] portCommandParts = portCommand.split(",");

        // Syntax error
        if (portCommandParts.length != 6) {
            return new FtpResponse(
                    FtpConstants.FTP_ERR_SYNTAX_CODE,
                    FtpConstants.FTP_ERR_SYNTAX_MSG);
        }

        // Extract the remote ip and the remote port from the line
        this.remoteIp = portCommandParts[0] + "." + portCommandParts[1] + "." + portCommandParts[2] + "." + portCommandParts[3];
        this.remotePort = Integer.parseInt(portCommandParts[4]) * 256 + Integer.parseInt(portCommandParts[5]);

        return new FtpResponse(
                FtpConstants.FTP_REP_CMD_OK,
                "PORT " + FtpConstants.FTP_REP_CMD_MSG);
    }

    public int getRemotePort() {
        return this.remotePort;
    }

    public String getRemoteIp() {
        return this.remoteIp;
    }
}
