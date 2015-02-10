package fr.univ.lille1.ftp.server.request.control;

import fr.univ.lille1.ftp.server.FtpPortManager;
import fr.univ.lille1.ftp.server.request.FtpRequest;
import fr.univ.lille1.ftp.server.request.FtpResponse;
import fr.univ.lille1.ftp.util.FtpConstants;

import java.io.IOException;

/**
 * Created by Warnant on 09-02-15.
 */
public class FtpPasvRequest extends FtpRequest {

    public FtpPasvRequest(String commandLine) {
        super(commandLine);
    }

    @Override
    public FtpResponse process() throws IOException {

        // Get a free number port
        int portNumber = FtpPortManager.getInstance().getNewPort();
        int portNumber1 = portNumber / 256;
        int portNumber2 = portNumber % 256;

        String[] localIp = FtpConstants.FTP_HOST_NAME.split(".");

        // Parse the pasv data response
        String PasvFormat = String.format(
                FtpConstants.FTP_REP_PASV_MODE_MSG,
                Integer.parseInt(localIp[0]),
                Integer.parseInt(localIp[1]),
                Integer.parseInt(localIp[2]),
                Integer.parseInt(localIp[3]),
                portNumber1,
                portNumber2
        );

        return new FtpResponse(FtpConstants.FTP_REP_PASV_MODE_CODE, PasvFormat);
    }
}
