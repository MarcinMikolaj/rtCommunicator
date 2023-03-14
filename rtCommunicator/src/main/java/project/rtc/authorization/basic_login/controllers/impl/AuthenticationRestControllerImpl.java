package project.rtc.authorization.basic_login.controllers.impl;

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

import project.rtc.authorization.basic_login.controllers.AuthenticationRestController;
import project.rtc.authorization.basic_login.services.AuthenticationService;
import project.rtc.authorization.basic_login.controllers.dto.LoginRequestPayload;
import project.rtc.authorization.basic_login.controllers.dto.LoginResponsePayload;
import project.rtc.authorization.basic_login.controllers.dto.LogoutRequestPayload;
import project.rtc.authorization.basic_login.services.AuthenticationServiceImpl;

@RestController	
// TODO: @RequiredArgsConstructur,
// constructure to remove, field to final
public class AuthenticationRestControllerImpl implements AuthenticationRestController {
	
	private final AuthenticationService loginService;
	
	public AuthenticationRestControllerImpl(AuthenticationServiceImpl loginServiceImpl) {
		this.loginService = loginServiceImpl;
	}
	
	//TODO: PostMapping
	@Override
	@RequestMapping(path = "/app/login", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<LoginResponsePayload> authenticate(HttpServletResponse response, @RequestBody LoginRequestPayload loginRequest){

		LoginResponsePayload loginResponsePayload = loginService.authenticate(response, loginRequest);

		if(loginResponsePayload.isAuthenticated())
			return new ResponseEntity<LoginResponsePayload>(loginResponsePayload, HttpStatus.OK);
		else
			return new ResponseEntity<LoginResponsePayload>(loginResponsePayload, HttpStatus.UNAUTHORIZED);
		//TODO: AOP, creare an exception handler, that will handle AuthenticationException and return the UNAUTHORIZED ResponseEntity
	}


	@Override
	@RequestMapping(path = "/app/logout", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> logout(@RequestBody LogoutRequestPayload logoutRequestPayload, HttpServletRequest request, HttpServletResponse response) throws IOException{
		
		loginService.logout(request, response, logoutRequestPayload);
		
		return new ResponseEntity<String>("logged out", HttpStatus.OK);		
	}
	
}
