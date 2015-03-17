package fr.univ.lille1.ftp.server.request.control;

import fr.univ.lille1.ftp.server.FtpThread;
import fr.univ.lille1.ftp.server.request.FtpRequest;
import fr.univ.lille1.ftp.server.request.FtpResponse;
import fr.univ.lille1.ftp.util.FtpConstants;

import java.io.IOException;

/**
 * FtpQuitRequest is the class associated to the QUIT ftp request
 * QUIT allows the user client to close the ftp control connection
 *
 * @author Quentin Warnant
 * @version 1.0
 */
public class FtpQuitRequest extends FtpRequest {

    private FtpThread currentThread;

    /**
     * Class constructor
     *
     * @param commandLine   String the request client command line
     * @param currentThread FtpThread the current client thread on the ftp server
     */
    public FtpQuitRequest(String commandLine, FtpThread currentThread) {
        super(commandLine);
        this.currentThread = currentThread;
    }

    @Override
    public FtpResponse process() throws IOException {

        // Change the current state of the ftp client thread
        this.currentThread.setRunning(false);

        return new FtpResponse(FtpConstants.FTP_REP_QUIT_CODE, FtpConstants.FTP_REP_QUIT_MSG);
    }
}
