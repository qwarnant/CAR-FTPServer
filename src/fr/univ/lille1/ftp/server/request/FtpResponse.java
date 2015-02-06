package fr.univ.lille1.ftp.server.request;

public class FtpResponse {

	private int code;
	private String message;
	
	public FtpResponse(int code, String message) {
		this.code = code;
		this.message = message;
	}

	public int getCode() {
		return this.code;
	}
	
	public String getMessage() {
		return this.message;
	}

    @Override
    public String toString() {
        return this.getCode() + " " + this.getMessage() + "\n";
    }
}
