package project.rtc.authorization.forgot_password.controllers.impl;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;
import project.rtc.authorization.forgot_password.controllers.ForgotPasswordRestController;
import project.rtc.authorization.forgot_password.reset_password_token.ChangePasswordRequest;
import project.rtc.authorization.forgot_password.ForgotPasswordRequest;
import project.rtc.authorization.forgot_password.services.ForgotPasswordService;

@RestController
@RequiredArgsConstructor
public class ForgotPasswordRestControllerImpl implements ForgotPasswordRestController {
	
	private final ForgotPasswordService forgotPasswordService;

	@Override
	public ResponseEntity<String> handleTheRequestToChangeThePassword(
			ForgotPasswordRequest forgotPasswordRequest, HttpServletResponse response) throws IOException {
		
			boolean result = forgotPasswordService.startTheProcessOfChangingThePassword(forgotPasswordRequest.getEmail()
					, response);
			
			if(result)
				return new ResponseEntity<String>(HttpStatus.OK);
			else
				return new ResponseEntity<String>(HttpStatus.NOT_FOUND);
			
    }

	@Override
	public ResponseEntity<?> handleTheAttemptToChangeThePassword(ChangePasswordRequest changePasswordRequest) {
		
	boolean result = forgotPasswordService.changePasswordIfTokenIsCorrect(changePasswordRequest.getToken()
			, changePasswordRequest.getPassword());
	
	if(result)
		return ResponseEntity.ok(null);
	
	return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
	
	}
	
}
