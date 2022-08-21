package project.rtc.authorization.controllers;

import java.io.IOException;
import java.security.Principal;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import project.rtc.authorization.credentials.Credentials;
import project.rtc.authorization.credentials.CredentialsRepository;
import project.rtc.authorization.credentials.CredentialsRepositoryImpl;
import project.rtc.authorization.oauth2.provider.AuthProvider;

@Controller
public class LoginPageController {
	
	private CredentialsRepository credentialsRepository;
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	public void setCredentialsRepository(CredentialsRepositoryImpl credentialsRepositoryImpl) {
		this.credentialsRepository = credentialsRepositoryImpl;
	}
	
	@Autowired
	public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
		this.passwordEncoder = passwordEncoder;
	}
    
	@RequestMapping(path = "/app/login")
	public String get() {
		
		String password = "12345";
		String email = "marcin";
		Credentials testCredentials = new Credentials(
				passwordEncoder.encode(password),
				email,
				"name",
				AuthProvider.local.toString());
		
		if(credentialsRepository.findByEmail(email) == null) {
			credentialsRepository.save(testCredentials);
		} else {
			System.out.println("Nie tworze konta drugi raz");
		}	
		return "LoginPage";
	}
	
	@RequestMapping(path = "/app/panel", method = RequestMethod.GET)
	public String getAppPanel2() {
		return "AppPanel";
	}
		
}
