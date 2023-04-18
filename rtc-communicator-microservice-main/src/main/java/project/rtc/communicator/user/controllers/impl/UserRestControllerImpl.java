package project.rtc.communicator.user.controllers.impl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import project.rtc.communicator.user.controllers.UserRestController;
import project.rtc.communicator.user.services.UserService;
import project.rtc.infrastructure.exception.exceptions.NoAuthorizationTokenException;
import project.rtc.infrastructure.exception.exceptions.UserNotFoundException;
import project.rtc.registration.dto.ProfilePicture;

@RestController
@RequiredArgsConstructor
public class UserRestControllerImpl implements UserRestController {
	private final UserService userService;

	@Override
	public ResponseEntity<?> getActualLoggedUser(HttpServletRequest httpServletRequest)
			throws UserNotFoundException, NoAuthorizationTokenException {
		return new ResponseEntity<>(userService.getUserAndLoadPicture(httpServletRequest), HttpStatus.OK);
	}

	@Override
	public ResponseEntity<?> updateUserNick(String nick, HttpServletRequest httpServletRequest)
			throws UserNotFoundException, NoAuthorizationTokenException {
		return new ResponseEntity<>(userService.updateUserNick(nick, httpServletRequest), HttpStatus.OK);
	}

	@Override
	public ResponseEntity<?> updateUserEmail(String email, HttpServletRequest httpServletRequest)
			throws UserNotFoundException, NoAuthorizationTokenException {
		return new ResponseEntity<>(userService.updateUserEmail(email, httpServletRequest), HttpStatus.OK);
	}

	@Override
	public ResponseEntity<?> updateUserPassword(String password, HttpServletRequest httpServletRequest)
			throws UserNotFoundException, NoAuthorizationTokenException {
		return new ResponseEntity<>(userService.updateUserPassword(password, httpServletRequest), HttpStatus.OK);
	}

	@Override
	public ResponseEntity<?> updateUserProfilePicture(ProfilePicture picture, HttpServletRequest httpServletRequest)
			throws UserNotFoundException, NoAuthorizationTokenException {
	    return new ResponseEntity<>(userService.updateUserPicture(picture, httpServletRequest), HttpStatus.OK);
	}

	@Override
	public ResponseEntity<?> deleteUser(String email, HttpServletRequest httpServletRequest
			, HttpServletResponse httpServletResponse)
			throws UserNotFoundException, NoAuthorizationTokenException {
		return new ResponseEntity<>(userService.deleteUser(email, httpServletRequest, httpServletResponse), HttpStatus.OK);
	}
	
}
