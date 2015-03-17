package fr.univ.lille1.ftp.server;

import fr.univ.lille1.ftp.util.FtpConstants;

import java.util.HashMap;
import java.util.Map;

/**
 * FtpUserManager is the class that handles the main user manager
 * Can be plug with a database system for more flexibility
 *
 * @author Quentin Warnant
 * @version 1.0
 */
public class FtpUserManager {

    private static FtpUserManager instance;
    private Map<String, String> users;

    /**
     * Class constructor
     */
    private FtpUserManager() {
        this.users = new HashMap<String, String>();
        this.users.put(FtpConstants.FTP_TEST_USERNAME, FtpConstants.FTP_TEST_PASSWORD);
    }

    /**
     * This method returns the current instance of the FtpUserManager
     * If there is no instance, the method creates one
     *
     * @return FtpUserManager the current instance
     */
    public static FtpUserManager getInstance() {
        if (instance == null) {
            instance = new FtpUserManager();
        }
        return instance;
    }

    /**
     * This method checks if the password for the specified user matches
     *
     * @param username String the requested username
     * @param password String the specified password
     * @return boolean true if the password is correct, false otherwise
     */
    public boolean checkPassword(String username, String password) {
        if (checkAnonymous(username)) {
            return true;
        }
        if (!this.users.containsKey(username)) {
            return false;
        }
        String storePassword = this.users.get(username);
        return storePassword.equals(password);
    }

    /**
     * This method checks if the specified username is in the ftp server database
     *
     * @param username String the requested username
     * @return boolean true if the user exists, false otherwise
     */
    public boolean containsUser(String username) {
        return checkAnonymous(username) || this.users.containsKey(username);
    }

    /**
     * This method checks if the user is anonymous and if the anonymous is enable
     *
     * @param username String the requested username
     * @return boolean if the user is anonymous, false otherwise
     */
    private boolean checkAnonymous(String username) {
        if (FtpConstants.IS_ANONYMOUS_MODE_ENABLED &&
                username.toLowerCase().equals(FtpConstants.FTP_ANONYMOUS_NAME)) {
            return true;
        }
        return false;
    }

}
