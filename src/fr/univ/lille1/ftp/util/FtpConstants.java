package fr.univ.lille1.ftp.util;

public interface FtpConstants {
	
	public static final String FTP_HOST_NAME = "127.0.0.1";
	public static final int FTP_SERVER_PORT = 1024;
    public static final boolean IS_ANONYMOUS_MODE_ENABLED = true;
	
	public static final String FTP_CMD_USER = "USER";
	public static final String FTP_CMD_PASS = "PASS";
    public static final String FTP_CMD_PORT = "PORT";
	public static final String FTP_ANONYMOUS_NAME = "anonymous";


    public static final int FTP_REP_CMD_OK = 200;
    public static final String FTP_REP_CMD_MSG = "command successful.";

    public static final int FTP_REP_READY_CODE = 220;
    public static final String FTP_REP_READY_MSG = "The date and time is ";

	public static final int FTP_REP_LOGIN_OK_CODE = 230;
	public static final String FTP_REP_LOGIN_OK_MSG = "User logged in, proceed. Logged out if appropriate.";
    public static final String FTP_REP_ANONYM_LOGIN_OK_MSG = "Guest login ok, access restrictions apply.";
	public static final int FTP_REP_NEED_USER_CODE = 331;
	public static final String FTP_REP_NEED_USER_MSG = "User name okay, need password.";
    public static final String FTP_REP_ANONYM_PASS_MSG = "Guest login ok, send your email address as password.";
	
	public static final int FTP_ERROR_INVALID_USER_PWD_CODE = 430;
	public static final String FTP_ERROR_INVALID_USER_PWD_MSG = "Invalid username or password";

    public static final int FTP_ERR_SYNTAX_CODE = 501;
    public static final String FTP_ERR_SYNTAX_MSG = "Syntax error on the parameters or arguments.";

    public static final int FTP_ERR_INVALID_COMMAND_CODE = 502;
    public static final String FTP_ERR_INVALID_COMMAND_MSG = "Command not implemented.";
	
	

}
