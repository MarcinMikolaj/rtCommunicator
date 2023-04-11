package project.rtc.authorization.forgot_password.controllers.rest.impl;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletResponse;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;
import project.rtc.authorization.forgot_password.controllers.rest.ForgotPasswordRestController;
import project.rtc.authorization.forgot_password.services.impl.ForgotPasswordServiceImpl;
import project.rtc.infrastructure.exception.exceptions.InvalidTokenException;

@RestController
@RequiredArgsConstructor
public class ForgotPasswordRestControllerImpl implements ForgotPasswordRestController {
	
	private final ForgotPasswordServiceImpl forgotPasswordService;

	@Override
	public ResponseEntity<?> handleTheRequestToChangeThePassword(String email, HttpServletResponse response)
			throws MessagingException {
			forgotPasswordService.startTheProcessOfChangingThePassword(email, response);
			return new ResponseEntity<>(HttpStatus.OK);
    }

	@Override
	public ResponseEntity<?> handleTheAttemptToChangeThePassword(String token, String password) throws InvalidTokenException {
		forgotPasswordService.changePasswordIfTokenIsCorrect(token, password);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
}
