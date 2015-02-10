package fr.univ.lille1.ftp.util;

public interface FtpConstants {

	public static final boolean DEBUG_ENABLED = true;
    public static final boolean IS_ANONYMOUS_MODE_ENABLED = true;
    public static final boolean CAN_ANONYNOUS_WRITE = true;

	public static final String FTP_HOST_NAME = "127.0.0.1";
	public static final int FTP_SERVER_PORT = 1024;

	public static char FTP_ASCII_TYPE = 'A';
	public static char FTP_BINARY_TYPE = 'I';
	
	public static final String FTP_CMD_USER = "USER";
	public static final String FTP_CMD_PASS = "PASS";
	public static final String FTP_CMD_PORT = "PORT";
	public static final String FTP_CMD_PASV = "PASV";
	public static final String FTP_CMD_TYPE = "TYPE";
	public static final String FTP_CMD_NLST = "NLST";
	public static final String FTP_CMD_CWD = "CWD";
	public static final String FTP_CMD_PWD = "PWD";
	public static final String FTP_CMD_CDUP = "CDUP";

	public static final String FTP_ANONYMOUS_NAME = "anonymous";

	public static final int FTP_REP_DATA_OK_CODE = 150;
	public static final String FTP_REP_DATA_OK_MSG = "Opening %s mode data connection for %s.";

	public static final int FTP_REP_CMD_OK_CODE = 200;
	public static final String FTP_REP_CMD_MSG = "command successful.";
	public static final String FTP_REP_TYPE_CMD_MSG = "Type set to %s.";

	public static final int FTP_REP_READY_CODE = 220;
	public static final String FTP_REP_READY_MSG = "The date and time is ";

	public static final int FTP_REP_TRANSF_COMPL_CODE = 226;
	public static final String FTP_REP_TRANSF_COMPL_MSG = "Transfer complete.";

	public static final int FTP_REP_PASV_MODE_CODE = 227;
	public static final String FTP_REP_PASV_MODE_MSG = "Entering Passive Mode (%d,%d,%d,%d,%d,%d)";

	public static final int FTP_REP_LOGIN_OK_CODE = 230;
	public static final String FTP_REP_LOGIN_OK_MSG = "User logged in, proceed. Logged out if appropriate.";
	public static final String FTP_REP_ANONYM_LOGIN_OK_MSG = "Guest login ok, access restrictions apply.";

	public static final int FTP_REP_PWD_CODE = 257;
	public static final String FTP_REP_PWD_MSG = "is the current directory.";
	public static final String FTP_REP_MKD_MSG = "directory created.";

	public static final int FTP_REP_NEED_USER_CODE = 331;
	public static final String FTP_REP_NEED_USER_MSG = "User name okay, need password.";
	public static final String FTP_REP_ANONYM_PASS_MSG = "Guest login ok, send your email address as password.";
	
	public static final int FTP_ERR_CONNECTION_FAILED_CODE = 425;
	public static final String FTP_ERR_CONNECTION_FAILED_MSG = "Can't open data connection.";

	public static final int FTP_ERROR_INVALID_USER_PWD_CODE = 430;
	public static final String FTP_ERROR_INVALID_USER_PWD_MSG = "Invalid username or password";

	public static final int FTP_ERR_SYNTAX_CODE = 501;
	public static final String FTP_ERR_SYNTAX_MSG = "Syntax error on the parameters or arguments.";

	public static final int FTP_ERR_INVALID_COMMAND_CODE = 502;
	public static final String FTP_ERR_INVALID_COMMAND_MSG = "Command not implemented.";
	
	public static final int FTP_ERR_DIR_EXISTS_CODE = 521;
	public static final String FTP_ERR_DIR_EXISTS_MSG = "directory already exists";

	public static final int FTP_ERR_ACTION_NOT_TAKEN = 550;
	public static final String FTP_ERR_FILE_NO_EXISTS_MSG = "No such file or directory.";
    public static final String FTP_ERR_ACCESS_DENIED_MSG = "Access is denied.";

}
