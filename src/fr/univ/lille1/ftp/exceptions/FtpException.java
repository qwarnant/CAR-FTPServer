package fr.univ.lille1.ftp.exceptions;

public class FtpException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int errorCode;

	public FtpException(int errorCode, String message) {
		this(message);
		this.errorCode = errorCode;
	}

	public FtpException(String message) {
		super(message);
	}

	public FtpException(Throwable cause) {
		super(cause);
	}

	public FtpException(String message, Throwable cause) {
		super(message, cause);
	}

}
