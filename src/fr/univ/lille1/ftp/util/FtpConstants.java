package fr.univ.lille1.ftp.util;

/**
 * FtpConstants is an interface which contains all usefull constants
 * for the FTP server and the test classes
 *
 * @author Quentin Warnant
 * @version 1.0
 */
public interface FtpConstants {

    /**
     * General FTP server constants
     */
	public static final boolean LOGGER_ENABLED = true;
    public static final boolean IS_ANONYMOUS_MODE_ENABLED = true;
    public static final boolean CAN_ANONYNOUS_WRITE = true;
    public static final String FTP_HOST_NAME = "127.0.0.1";
    public static final int FTP_SERVER_PORT = 1024;
    public static final String FTP_LOGGER_NAME = "ftplog";

    /**
     * Test account information constants
     */
    public static final String FTP_TEST_USERNAME = "johndoe";
    public static final String FTP_TEST_PASSWORD = "foo";
    public static final String FTP_ANONYMOUS_NAME = "anonymous";

    /**
     * Data transfer type constants
     */
	public static char FTP_ASCII_TYPE = 'A';
	public static char FTP_BINARY_TYPE = 'I';

    /**
     * All available ftp request constants on the server
     */
	public static final String FTP_CMD_USER = "USER";
	public static final String FTP_CMD_PASS = "PASS";
	public static final String FTP_CMD_PORT = "PORT";
	public static final String FTP_CMD_PASV = "PASV";
	public static final String FTP_CMD_TYPE = "TYPE";
	public static final String FTP_CMD_NLST = "NLST";
	public static final String FTP_CMD_LIST = "LIST";
	public static final String FTP_CMD_CWD = "CWD";
	public static final String FTP_CMD_PWD = "PWD";
    public static final String FTP_CMD_XPWD = "XPWD";
	public static final String FTP_CMD_CDUP = "CDUP";
    public static final String FTP_CMD_MKD = "MKD";
    public static final String FTP_CMD_XMKD = "XMKD";
    public static final String FTP_CMD_RMD = "RMD";
    public static final String FTP_CMD_DELE = "DELE";
    public static final String FTP_CMD_XRMD = "XRMD";
    public static final String FTP_CMD_RETR = "RETR";
    public static final String FTP_CMD_STOR = "STOR";
    public static final String FTP_CMD_QUIT = "QUIT";
    public static final String FTP_CMD_SYST = "SYST";

    /**
     * FTP request/response message and code constants
     */
	public static final int FTP_REP_DATA_OK_CODE = 150;
	public static final String FTP_REP_DATA_OK_MSG = "Opening %s mode data connection for %s.";

	public static final int FTP_REP_CMD_OK_CODE = 200;
	public static final String FTP_REP_CMD_MSG = "command successful.";
	public static final String FTP_REP_TYPE_CMD_MSG = "Type set to %s.";
	
	public static final int FTP_REP_SYST_CODE = 215;
    //public static final String FTP_REP_SYST_MSG = "UNIX Type: L8";
    public static final String FTP_REP_SYST_MSG = "Windows NT";


    public static final int FTP_REP_READY_CODE = 220;
	public static final String FTP_REP_READY_MSG = "The date and time is ";

    public static final int FTP_REP_QUIT_CODE = 221;
    public static final String FTP_REP_QUIT_MSG = "Goodbye.";

	public static final int FTP_REP_TRANSF_COMPL_CODE = 226;
	public static final String FTP_REP_TRANSF_COMPL_MSG = "Transfer complete.";

	public static final int FTP_REP_PASV_MODE_CODE = 227;
	public static final String FTP_REP_PASV_MODE_MSG = "Entering Passive Mode (%d,%d,%d,%d,%d,%d)";

	public static final int FTP_REP_LOGIN_OK_CODE = 230;
	public static final String FTP_REP_LOGIN_OK_MSG = "User logged in, proceed. Logged out if appropriate.";
	public static final String FTP_REP_ANONYM_LOGIN_OK_MSG = "Guest login ok, access restrictions apply.";

	public static final int FTP_REP_PWD_CODE = 250;
	public static final String FTP_REP_PWD_MSG = "is the current directory.";
	public static final String FTP_REP_MKD_MSG = "directory created.";
    public static final String FTP_REP_RMD_MSG = "directory removed.";

	public static final int FTP_REP_NEED_USER_CODE = 331;
	public static final String FTP_REP_NEED_USER_MSG = "User name okay, need password.";
	public static final String FTP_REP_ANONYM_PASS_MSG = "Guest login ok, send your email address as password.";
	
	public static final int FTP_ERR_CONNECTION_FAILED_CODE = 425;
	public static final String FTP_ERR_CONNECTION_FAILED_MSG = "Can't open data connection.";

	public static final int FTP_ERR_INVALID_USER_PWD_CODE = 430;
	public static final String FTP_ERR_INVALID_USER_PWD_MSG = "Invalid username or password";

	public static final int FTP_ERR_SYNTAX_CODE = 501;
	public static final String FTP_ERR_SYNTAX_MSG = "Syntax error on the parameters or arguments.";

    public static final int FTP_ERR_BAD_SEQ_CODE = 503;
    public static final String FTP_ERR_BAD_SEQ_MSG = "Bad sequence of commands.";

	public static final int FTP_ERR_INVALID_COMMAND_CODE = 502;
	public static final String FTP_ERR_INVALID_COMMAND_MSG = "Command not implemented.";
	
	public static final int FTP_ERR_DIR_EXISTS_CODE = 521;
	public static final String FTP_ERR_DIR_EXISTS_MSG = "directory already exists";

	public static final int FTP_ERR_ACTION_NOT_TAKEN = 550;
	public static final String FTP_ERR_FILE_NO_EXISTS_MSG = "No such file or directory.";
    public static final String FTP_ERR_ACCESS_DENIED_MSG = "Access is denied.";

}
