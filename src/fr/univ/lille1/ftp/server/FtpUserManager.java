package fr.univ.lille1.ftp.server;

import fr.univ.lille1.ftp.util.FtpConstants;

import java.util.HashMap;
import java.util.Map;

public class FtpUserManager {

    private static FtpUserManager instance;
    private Map<String, String> users;

    private FtpUserManager() {
        this.users = new HashMap<String, String>();
        this.users.put("johndoe", "foo");
    }

    public static FtpUserManager getInstance() {
        if (instance == null) {
            instance = new FtpUserManager();
        }
        return instance;
    }

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

    public boolean containsUser(String username) {
        return checkAnonymous(username) || this.users.containsKey(username);
    }

    private boolean checkAnonymous(String username) {
        if (FtpConstants.IS_ANONYMOUS_MODE_ENABLED &&
                username.toLowerCase().equals(FtpConstants.FTP_ANONYMOUS_NAME)) {
            return true;
        }
        return false;
    }

}
