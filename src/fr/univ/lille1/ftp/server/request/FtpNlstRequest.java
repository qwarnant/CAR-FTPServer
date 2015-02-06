package fr.univ.lille1.ftp.server.request;

import fr.univ.lille1.ftp.server.FtpServer;

import java.io.DataOutputStream;
import java.io.IOException;

/**
 * Created by Warnant on 06-02-15.
 */
public class FtpNlstRequest extends FtpRequest {

    private DataOutputStream out;


    public FtpNlstRequest(String commandLine, DataOutputStream out) {
        super(commandLine);
        this.out = out;
    }

    @Override
    public FtpResponse process() throws IOException {
        String targetDirectory = this.commandLine.substring(5, commandLine.length());

        // If there is now argument directory, get the default
        if(targetDirectory.length() == 0) {
            targetDirectory = FtpServer.getFtpDirectory();
        }

        return new FtpResponse(0, "");
    }
}
