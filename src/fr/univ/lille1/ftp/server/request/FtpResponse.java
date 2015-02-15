package fr.univ.lille1.ftp.server.request;

/**
 * FtpResponse is the class that handles the main response parameters
 *
 * @author Quentin Warnant
 * @version 1.0
 */
public class FtpResponse {

    private int code;
    private String message;

    /**
     * Class constructor
     *
     * @param code    int the ftp response code
     * @param message String the ftp reponse message
     */
    public FtpResponse(int code, String message) {
        this.code = code;
        this.message = message;
    }

    /**
     * Returns the current ftp response code
     *
     * @return int the code
     */
    public int getCode() {
        return this.code;
    }

    /**
     * Returns the current ftp response message
     *
     * @return String the message
     */
    public String getMessage() {
        return this.message;
    }

    @Override
    public String toString() {
        return this.getCode() + " " + this.getMessage() + "\n";
    }
}
