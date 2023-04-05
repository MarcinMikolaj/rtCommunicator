package project.rtc.communicator.user.controllers.impl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import project.rtc.communicator.user.dto.UserRequestDto;
import project.rtc.communicator.user.controllers.UserRestController;
import project.rtc.communicator.user.services.UserService;
import project.rtc.infrastructure.exception.exceptions.NoAuthorizationTokenException;
import project.rtc.infrastructure.exception.exceptions.UserNotFoundException;

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
	public ResponseEntity<?> updateUserNick(@RequestBody @Validated UserRequestDto body
			, HttpServletRequest httpServletRequest) throws UserNotFoundException, NoAuthorizationTokenException {
		return new ResponseEntity<>(userService.updateUserNick(body.getNick(), httpServletRequest), HttpStatus.OK);
	}

	@Override
	public ResponseEntity<?> updateUserEmail(UserRequestDto body, HttpServletRequest httpServletRequest)
			throws UserNotFoundException, NoAuthorizationTokenException {
		return new ResponseEntity<>(userService.updateUserEmail(body.getEmail(), httpServletRequest), HttpStatus.OK);
	}

	@Override
	public ResponseEntity<?> updateUserPassword(UserRequestDto body, HttpServletRequest httpServletRequest)
			throws UserNotFoundException, NoAuthorizationTokenException {
		return new ResponseEntity<>(userService.updateUserPassword(body.getEmail(), body.getPassword(), httpServletRequest)
				, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<?> updateUserProfilePicture(UserRequestDto body, HttpServletRequest httpServletRequest)
			throws UserNotFoundException, NoAuthorizationTokenException {
	    return new ResponseEntity<>(userService.updateUserPicture(body.getProfilePicture(), httpServletRequest)
				, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<?> deleteUser(UserRequestDto body,
                                        HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse)
			throws UserNotFoundException, NoAuthorizationTokenException {
		return new ResponseEntity<>(userService.deleteUser(body.getEmail(), httpServletRequest, httpServletResponse)
				, HttpStatus.OK);
	}
	
}
