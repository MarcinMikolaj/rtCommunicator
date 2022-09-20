package project.rtc.authorization.basic_login.controllers;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import project.rtc.authorization.basic_login.controllers.pojo.LoginRequestPayload;
import project.rtc.authorization.basic_login.controllers.pojo.LoginResponsePayload;
import project.rtc.authorization.basic_login.controllers.pojo.LogoutRequestPayload;

@RestController	
public class AuthenticationRestController {
	
	private AuthenticationService loginService;
	
	public AuthenticationRestController(AuthenticationServiceImpl loginServiceImpl) {
		this.loginService = loginServiceImpl;
	}
	
	@RequestMapping(path = "/app/login", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<LoginResponsePayload> authenticate(HttpServletResponse response, @RequestBody LoginRequestPayload loginRequest){
		
		LoginResponsePayload loginResponsePayload = loginService.authenticate(response, loginRequest);
		
		if(loginResponsePayload.isAuthenticated())
			return new ResponseEntity<LoginResponsePayload>(loginResponsePayload, HttpStatus.OK);	
		else
			return new ResponseEntity<LoginResponsePayload>(loginResponsePayload, HttpStatus.UNAUTHORIZED);
		
	}
	
	
	@RequestMapping(path = "/app/logout", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> logout(@RequestBody LogoutRequestPayload logoutRequestPayload, HttpServletRequest request, HttpServletResponse response) throws IOException{
		
		loginService.logout(request, response, logoutRequestPayload);
		
		return new ResponseEntity<String>("logged out", HttpStatus.OK);		
	}
	
}
