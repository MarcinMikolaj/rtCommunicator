package project.rtc.test.user;

import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.nimbusds.jose.shaded.json.JSONObject;

import project.rtc.exceptions.NoAuthorizationTokenException;
import project.rtc.exceptions.UserNotFoundException;
import project.rtc.registration.ProfilePicture;

@RestController
public class UserRestController {
	
	private UserService userService;
	
	
	public UserRestController(UserServiceImpl userServiceImpl) {
		this.userService = userServiceImpl;
		
	}
	
	@RequestMapping(path = "/app/account/get", method = RequestMethod.GET)
	public ResponseEntity<User> getActualLoggedUser(HttpServletRequest httpServletRequest){
		
		User user;
		
		try {
			 user = userService.getUserAndLoadPicture(httpServletRequest);	
			 return new ResponseEntity<User>(user, HttpStatus.OK);
		} catch (UserNotFoundException e) {
			e.printStackTrace();
		} catch (NoAuthorizationTokenException e) {
			e.printStackTrace();
		}
		
		
		return new ResponseEntity<User>(new User(), HttpStatus.NOT_FOUND);
	}
	
	
	// Handles the request to change the user's nick
	@RequestMapping(path = "/app/account/update/nick", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<UserResponseBody> updateUserNick(@RequestBody Map<String, ?> nick, HttpServletRequest httpServletRequest) {
		
		JSONObject jsonObject = new JSONObject(nick);
		String userNick = jsonObject.getAsString("nick");
		
		UserResponseBody userResponseBody = userService.updateUserNick(userNick, httpServletRequest);
		
		return new ResponseEntity<UserResponseBody>(userResponseBody, HttpStatus.OK);
		
	}
	
	
	// Handles the request to change the user's email
	@RequestMapping(path = "/app/account/update/email", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<UserResponseBody> updateUserEmail(@RequestBody Map<String, ?> email, HttpServletRequest httpServletRequest) {
			
		JSONObject jsonObject = new JSONObject(email);
		String userEmail = jsonObject.getAsString("email");
			
		UserResponseBody userResponseBody = userService.updateUserEmail(userEmail, httpServletRequest);
			
		return new ResponseEntity<UserResponseBody>(userResponseBody, HttpStatus.OK);
			
	}
	
	// Handles the request to change the user's email
	@RequestMapping(path = "/app/account/update/password", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<UserResponseBody> updateUserPassword(@RequestBody Map<String, ?> attributes, HttpServletRequest httpServletRequest) {
				
		JSONObject jsonObject = new JSONObject(attributes);
		String userEmail = jsonObject.getAsString("email");
		String userPassword = jsonObject.getAsString("password");
				
		UserResponseBody userResponseBody = userService.updateUserPassword(userEmail, userPassword, httpServletRequest);
				
		return new ResponseEntity<UserResponseBody>(userResponseBody, HttpStatus.OK);
				
	}
	
	
	// Handles the request to change the user's picture
	@RequestMapping(path = "/app/account/update/picture", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<UserResponseBody> updateUserProfilePicture(@RequestBody ProfilePicture profilePicture, HttpServletRequest httpServletRequest) {
		
		System.out.print(profilePicture);
		System.out.print(profilePicture.getFileInBase64());
		
		UserResponseBody userResponseBody = userService.updateUserPicture(profilePicture, httpServletRequest);
		
	    return new ResponseEntity<UserResponseBody>(userResponseBody, HttpStatus.OK);
		
	}
	
	
	// Handles the request to delete user
	@RequestMapping(path = "/app/account/delete", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<UserResponseBody> deleteUser(@RequestBody Map<String, ?> email,
			HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
		
		JSONObject jsonObject = new JSONObject(email);
		String userEmail = jsonObject.getAsString("email");
		
		UserResponseBody userResponseBody = userService.deleteUser(userEmail, httpServletRequest, httpServletResponse);
		
		return new ResponseEntity<UserResponseBody>(userResponseBody, HttpStatus.OK);
	}
	
}
