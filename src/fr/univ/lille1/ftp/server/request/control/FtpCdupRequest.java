package fr.univ.lille1.ftp.server.request.control;

/**
 * FtpCdupRequest is the class associated to the CDUP ftp request
 * CDUP allows the user to go up in the current file system
 *
 * @author Quentin Warnant
 * @version 1.0
 */

public class FtpCdupRequest extends FtpCwdRequest {

    /**
     * Class constructor
     *
     * @param commandLine      String the request client command line
     * @param currentDirectory String the user current directory on the ftp server
     */
    public FtpCdupRequest(String commandLine, String currentDirectory) {
        super(commandLine + " ..", currentDirectory);
    }


}
