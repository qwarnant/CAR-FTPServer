package fr.univ.lille1.ftp.util;

public interface FtpConstants {
	
	public static final String FTP_HOST_NAME = "127.0.0.1";
	public static final int FTP_SERVER_PORT = 1024;
	
	public static final String FTP_CMD_USER = "USER";
	public static final String FTP_CMD_PSWD = "PSWD";
	
	

	public static final int FTP_RESPONSE_NEED_USER_CODE = 331;
	public static final String FTP_RESPONSE_NEED_USER_MSG = "User name okay, need password.";
	
	
	
	public static final int FTP_ERROR_INVALID_USER_PWD_CODE = 430;
	public static final String FTP_ERROR_INVALID_USER_PWD_MSG = "Invalid username or password";

	
	

}
