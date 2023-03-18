package project.rtc.communicator.user.controllers.impl;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.nimbusds.jose.shaded.json.JSONObject;

import project.rtc.communicator.user.dto.User;
import project.rtc.communicator.user.dto.UserResponseBody;
import project.rtc.communicator.user.controllers.UserRestController;
import project.rtc.communicator.user.services.UserService;
import project.rtc.exceptions.NoAuthorizationTokenException;
import project.rtc.exceptions.UserNotFoundException;
import project.rtc.registration.dto.ProfilePicture;

@RestController
@RequiredArgsConstructor
public class UserRestControllerImpl implements UserRestController {
	
	private final UserService userService;

	@Override
	public ResponseEntity<User> getActualLoggedUser(HttpServletRequest httpServletRequest) {
		
		User user;
		
		try {
			 user = userService.getUserAndLoadPicture(httpServletRequest);	
			 return new ResponseEntity<User>(user, HttpStatus.OK);
		} catch (UserNotFoundException e) {
			e.printStackTrace();
		} catch (NoAuthorizationTokenException e) {
			e.printStackTrace();
		}

		return new ResponseEntity<>(new User(), HttpStatus.NOT_FOUND);
	}

	@Override
	public ResponseEntity<UserResponseBody> updateUserNick(Map<String, ?> nick
			, HttpServletRequest httpServletRequest) {
		
		JSONObject jsonObject = new JSONObject(nick);
		String userNick = jsonObject.getAsString("nick");
		
		UserResponseBody userResponseBody = userService.updateUserNick(userNick, httpServletRequest);
		
		return new ResponseEntity<>(userResponseBody, HttpStatus.OK);
		
	}

	@Override
	public ResponseEntity<UserResponseBody> updateUserEmail(Map<String, ?> email
			, HttpServletRequest httpServletRequest) {
			
		JSONObject jsonObject = new JSONObject(email);
		String userEmail = jsonObject.getAsString("email");
			
		UserResponseBody userResponseBody = userService.updateUserEmail(userEmail, httpServletRequest);
			
		return new ResponseEntity<>(userResponseBody, HttpStatus.OK);
			
	}

	@Override
	public ResponseEntity<UserResponseBody> updateUserPassword(Map<String, ?> attributes
			, HttpServletRequest httpServletRequest) {
				
		JSONObject jsonObject = new JSONObject(attributes);
		String userEmail = jsonObject.getAsString("email");
		String userPassword = jsonObject.getAsString("password");
				
		UserResponseBody userResponseBody = userService.updateUserPassword(userEmail, userPassword, httpServletRequest);
				
		return new ResponseEntity<>(userResponseBody, HttpStatus.OK);
				
	}

	@Override
	public ResponseEntity<UserResponseBody> updateUserProfilePicture(ProfilePicture profilePicture
			, HttpServletRequest httpServletRequest) {

		UserResponseBody userResponseBody = userService.updateUserPicture(profilePicture, httpServletRequest);
		
	    return new ResponseEntity<>(userResponseBody, HttpStatus.OK);
		
	}

	@Override
	public ResponseEntity<UserResponseBody> deleteUser(Map<String, ?> email,
			HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
		
		JSONObject jsonObject = new JSONObject(email);
		String userEmail = jsonObject.getAsString("email");
		
		UserResponseBody userResponseBody = userService.deleteUser(userEmail, httpServletRequest, httpServletResponse);
		
		return new ResponseEntity<>(userResponseBody, HttpStatus.OK);
	}
	
}
