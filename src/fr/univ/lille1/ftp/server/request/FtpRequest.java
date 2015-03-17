package fr.univ.lille1.ftp.server.request;

import java.io.IOException;

/**
 * FtpRequest is the class that handles the main request parameters
 * @version 1.0
 * @author Quentin Warnant
 */
public abstract class FtpRequest {

    protected String commandLine;

    /**
     * Constructor
     *
     * @param commandLine string the input command line
     */
    public FtpRequest(String commandLine) {
        this.commandLine = commandLine;
    }

    /**
     * process method describes the behaviour for the current ftp request
     *
     * @return FtpResponse the server ftp response after the processing
     * @throws IOException
     */
    public abstract FtpResponse process() throws IOException;

    @Override
    public String toString() {
        return this.commandLine;
    }
}
