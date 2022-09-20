package project.rtc.exceptions;

public class UserNotFoundException extends Exception {

	private static final long serialVersionUID = 6318106571126697764L;
	
    public UserNotFoundException() {};
	
	public UserNotFoundException(String message){super("User not found.");};

}
