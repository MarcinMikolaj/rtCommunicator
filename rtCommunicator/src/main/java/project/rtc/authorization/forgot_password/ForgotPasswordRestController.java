package project.rtc.authorization.forgot_password;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ForgotPasswordRestController {
	
	private ForgotPasswordService forgotPasswordService;
	
	@Autowired
	public void setForgotPasswordService(ForgotPasswordService forgotPasswordService) {
		this.forgotPasswordService = forgotPasswordService;
	}
	
	@RequestMapping(path = "/app/forgot/send", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> handleTheRequestToChangeThePassword(
			@RequestBody ForgotPasswordRequest forgotPasswordRequest, HttpServletResponse response) throws IOException{
		
		try {
			forgotPasswordService.startTheProcessOfChangingThePassword(forgotPasswordRequest.getEmail(), response);
			return new ResponseEntity<String>(HttpStatus.OK);
		
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return new ResponseEntity<String>(HttpStatus.NOT_FOUND);
		}
	
}	
	
	@RequestMapping(path = "/app/forgot/credentials/update", method = RequestMethod.POST)
	public ResponseEntity<?> handleTheAttemptToChangeThePassword(@RequestBody ChangePasswordRequest changePasswordRequest){
		
	boolean result = forgotPasswordService.changePasswordIfTokenIsCorrect(changePasswordRequest.getToken(), changePasswordRequest.getPassword());
	
	if(result)
		return ResponseEntity.ok(null);
	
	return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
	
	}
	
}
