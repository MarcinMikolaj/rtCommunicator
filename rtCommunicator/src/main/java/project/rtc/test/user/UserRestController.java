package project.rtc.test.user;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserRestController {
	
	private UserService userService;
	
	public UserRestController(UserServiceImpl userServiceImpl) {
		this.userService = userServiceImpl;
	}
	
	
	// Handles the request to change the user's nick
	@RequestMapping(path = "/app/rtc/user/update/nick", method = RequestMethod.PUT)
	public ResponseEntity<UserResponseBody> updateUserNick(@RequestBody UserRequestBody userRequestBody, HttpServletRequest httpServletRequest) {
		
		UserResponseBody userResponseBody = userService.updateUserNick(userRequestBody, httpServletRequest);
		
		return new ResponseEntity<UserResponseBody>(userResponseBody, HttpStatus.OK);
	}
	
	
	// Handles the request to change the user's picture
	@RequestMapping(path = "/app/rtc/user/update/picture", method = RequestMethod.PUT)
	public ResponseEntity<UserResponseBody> updateUserProfilePicture(@RequestBody UserRequestBody userRequestBody, HttpServletRequest httpServletRequest) {
		
		UserResponseBody userResponseBody = userService.updateUserPicture(userRequestBody, httpServletRequest);
		
		return new ResponseEntity<UserResponseBody>(userResponseBody, HttpStatus.OK);
	}
	
	
	// Handles the request to delete user
	@RequestMapping(path = "/app/rtc/user/delete", method = RequestMethod.DELETE)
	public ResponseEntity<UserResponseBody> deleteUser(@RequestBody UserRequestBody userRequestBody, HttpServletRequest httpServletRequest) {
		
		UserResponseBody userResponseBody = userService.deleteUser(userRequestBody, httpServletRequest);
		
		return new ResponseEntity<UserResponseBody>(userResponseBody, HttpStatus.OK);
	}
	
}
