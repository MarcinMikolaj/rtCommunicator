package project.rtc.test.user;

import javax.servlet.http.HttpServletRequest;

import project.rtc.exceptions.NoAuthorizationTokenException;
import project.rtc.exceptions.UserNotFoundException;

public interface UserService {
	
	public User getUser(HttpServletRequest httpServletRequest) throws UserNotFoundException, NoAuthorizationTokenException;
	
	public UserResponseBody deleteUser(UserRequestBody userRequestBody ,HttpServletRequest httpServletRequest);
	public UserResponseBody updateUserNick(UserRequestBody userRequestBody, HttpServletRequest httpServletRequest);
	public UserResponseBody updateUserPicture(UserRequestBody userRequestBody, HttpServletRequest httpServletRequest);

}
