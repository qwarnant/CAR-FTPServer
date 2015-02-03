package fr.univ.lille1.ftp.server;

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
		if (this.users.containsKey(username)) {
			return false;
		}
		String storePassword = this.users.get(username);
		return storePassword.equals(password);
	}

	public boolean containsUser(String username) {
		return this.users.containsKey(username);
	}

}
