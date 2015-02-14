package fr.univ.lille1.ftp.server.request.control;

import fr.univ.lille1.ftp.server.FtpThread;
import fr.univ.lille1.ftp.server.request.FtpRequest;
import fr.univ.lille1.ftp.server.request.FtpResponse;
import fr.univ.lille1.ftp.util.FtpConstants;

import java.io.IOException;

/**
 * Created by Warnant on 14-02-15.
 */
public class FtpQuitRequest extends FtpRequest {

    private FtpThread currentThread;

    public FtpQuitRequest(String commandLine, FtpThread currentThread) {
        super(commandLine);
        this.currentThread = currentThread;
    }

    @Override
    public FtpResponse process() throws IOException {

        this.currentThread.setRunning(false);

        return new FtpResponse(FtpConstants.FTP_REP_QUIT_CODE, FtpConstants.FTP_REP_QUIT_MSG);
    }
}
