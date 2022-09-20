package project.rtc.authorization.basic_login.controllers;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import project.rtc.authorization.basic_login.controllers.pojo.LoginPayloadRequest;
import project.rtc.authorization.basic_login.controllers.pojo.LoginPayloadResponse;
import project.rtc.authorization.basic_login.credentials.Credentials;
import project.rtc.authorization.basic_login.credentials.CredentialsRepository;
import project.rtc.authorization.basic_login.credentials.CredentialsRepositoryImpl;
import project.rtc.authorization.basic_login.credentials.CredentialsService;
import project.rtc.authorization.basic_login.credentials.CredentialsServiceImpl;
import project.rtc.authorization.security.jwt.JwtTokenProvider;
import project.rtc.utils.CookieUtils;

@RestController
public class LoginPageRestControler {
	
	private AuthenticationManager authenticationManager;
	private JwtTokenProvider jwtTokenProvider;
	private CredentialsService credentialsService;
	
	@Autowired
	public void setAuthenticationManager(AuthenticationManager authenticationManager) {
		this.authenticationManager = authenticationManager;
	}
	
	@Autowired
	public void setJwtTokenProvider(JwtTokenProvider jwtTokenProvider) {
		this.jwtTokenProvider = jwtTokenProvider;
	}
	
	@Autowired
	public void setCredentialsService(CredentialsServiceImpl credentialsServiceImpl) {
		this.credentialsService = credentialsServiceImpl;
	}
	
	@RequestMapping(path = "/app/login", method = RequestMethod.POST)
	public ResponseEntity<LoginPayloadResponse> authenticateUser(@RequestBody LoginPayloadRequest loginPayloadRequest, HttpServletResponse response){
		
		
		if(!credentialsService.checkIfCredentialsExist(loginPayloadRequest)) {
			return new ResponseEntity<LoginPayloadResponse>(null, null, HttpStatus.UNAUTHORIZED);
		}
		
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(
						loginPayloadRequest.getEmail(), loginPayloadRequest.getPassword()
						)
		);
		
		String jwtToken = jwtTokenProvider.createJwtToken(authentication, loginPayloadRequest.getEmail(), null, null);
		
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add("Authorization", "Bearer " + jwtToken);
	
		LoginPayloadResponse loginPayloadResponse = new LoginPayloadResponse(jwtToken);
		CookieUtils.addCookie(response, "jwt", jwtToken, 600000);
		
		
		
		return new ResponseEntity<LoginPayloadResponse>(loginPayloadResponse, httpHeaders, HttpStatus.OK);	
	}
	
	
}
