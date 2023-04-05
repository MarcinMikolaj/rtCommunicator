package project.rtc.infrastructure.exception.exceptions;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class UserNotFoundException extends Exception {

	private static final long serialVersionUID = 6318106571126697764L;
	public UserNotFoundException(String message){super("User not found.");};
}
