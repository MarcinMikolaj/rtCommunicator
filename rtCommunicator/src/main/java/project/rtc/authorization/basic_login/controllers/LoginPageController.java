package project.rtc.authorization.basic_login.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import project.rtc.authorization.basic_login.credentials.Credentials;
import project.rtc.authorization.basic_login.credentials.CredentialsRepository;
import project.rtc.authorization.basic_login.credentials.CredentialsRepositoryImpl;
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
		
		test();
			
		return "LoginPage";
	}
	
	
	@RequestMapping(path = "/app/panel", method = RequestMethod.GET)
	public String getAppPanel2() {
		return "AppPanel";
	}
	
	
	
	
	public void test() {
		//Create test account
				Credentials testCredentials1 = new Credentials(
						passwordEncoder.encode("12345"),
						"marcin",
						"name",
						AuthProvider.local.toString());
				
				
				Credentials testCredentials2 = new Credentials(
						passwordEncoder.encode("12345"),
						"marcin09876a2@gmail.com",
						"marcin",
						AuthProvider.local.toString());
				
				if(credentialsRepository.findByEmail(testCredentials1.getEmail()) == null ) 
					credentialsRepository.save(testCredentials1);	
				
				
				if(credentialsRepository.findByEmail(testCredentials2.getEmail()) == null ) 
					credentialsRepository.save(testCredentials2);
	}
		
}
