package project.rtc.authorization.basic_login.controllers.impl;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import project.rtc.authorization.basic_login.controllers.AuthenticationRestController;
import project.rtc.authorization.basic_login.controllers.impl.exceptions.AuthenticationException;
import project.rtc.authorization.basic_login.services.AuthenticationService;
import project.rtc.authorization.basic_login.controllers.dto.LoginRequestPayload;
import project.rtc.authorization.basic_login.controllers.dto.LoginResponsePayload;
import project.rtc.authorization.basic_login.controllers.dto.LogoutRequestPayload;

@RestController
@RequiredArgsConstructor
public class AuthenticationRestControllerImpl implements AuthenticationRestController {
	
	private final AuthenticationService loginService;

	@Override
	@PostMapping(path = "/app/login", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<LoginResponsePayload> authenticate(HttpServletResponse response, @RequestBody LoginRequestPayload loginRequest) throws AuthenticationException {

		return new ResponseEntity<LoginResponsePayload>(loginService.authenticate(response, loginRequest), HttpStatus.OK);

	}


	@Override
	@PostMapping(path = "/app/logout", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> logout(@RequestBody LogoutRequestPayload logoutRequestPayload, HttpServletRequest request, HttpServletResponse response) throws IOException{
		
		loginService.logout(request, response, logoutRequestPayload);
		
		return new ResponseEntity<String>("logged out", HttpStatus.OK);		
	}
	
}
