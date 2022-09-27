package project.rtc.test.user;

import javax.servlet.http.HttpServletRequest;

import project.rtc.exceptions.NoAuthorizationTokenException;
import project.rtc.exceptions.UserNotFoundException;
import project.rtc.registration.ProfilePicture;

public interface UserService {
	
	// Returns the user with a registered and saved account, otherwise he throws UserNotFoundException
	public User getUser(HttpServletRequest httpServletRequest) throws UserNotFoundException, NoAuthorizationTokenException;
	
	// Returns the user with a registered and saved account, it also loads the photo to the returned user.
	// Throw UserNotFoundException if user not found.
	public User getUserAndLoadPicture(HttpServletRequest httpServletRequest) throws UserNotFoundException, NoAuthorizationTokenException;
	
	// The method allows you to delete a user account. Ensures that the user will be removed from any room they were previously assigned to.
	public UserResponseBody deleteUser(String password ,HttpServletRequest httpServletRequest);
	
	public UserResponseBody updateUserNick(String nick, HttpServletRequest httpServletRequest);
	public UserResponseBody updateUserPicture(ProfilePicture profilePicture, HttpServletRequest httpServletRequest);
	public UserResponseBody updateUserEmail(String email, HttpServletRequest httpServletRequest);
	public UserResponseBody updateUserPassword(String email, String password, HttpServletRequest httpServletRequest);
	
	

}
