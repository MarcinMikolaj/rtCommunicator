package project.rtc.exceptions;

public class NoAuthorizationTokenException extends Exception {

	private static final long serialVersionUID = -5987005698163980893L;
	
    public NoAuthorizationTokenException() {};
	
	public NoAuthorizationTokenException(String message){super("No authorization token.");};

}
