package project.rtc;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import project.rtc.authorization.credentials.Credentials;
import project.rtc.authorization.credentials.CredentialsRepository;
import project.rtc.authorization.credentials.CredentialsRepositoryImpl;

@Controller
public class LoginPageController {
	
    private CredentialsRepository credentialsRepository;
	
	@Autowired
	public void setCredentialsRepository(CredentialsRepositoryImpl credentialsRepositoryImpl) {
		this.credentialsRepository = credentialsRepositoryImpl;
	}
	
	@RequestMapping(path = "/app/login")
	public String get() {
		return "LoginPage";
	}
	
	@RequestMapping(path = "/app/panel")
	public String get1(Principal principal) {
		
		System.out.println("Principla: " + principal.getName());
		
		return "index";
	}
	
	@RequestMapping(path = "/app/logout/success", method = RequestMethod.GET)
	public String ge3t() {
		return "logout";
	}
	
	@RequestMapping(path = "/test", method = RequestMethod.GET)
	public String ge2t() {
		
//		Credentials credentials = new Credentials("login123", "haslo123");
//		credentialsRepository.save(credentials);
		
		
//		System.out.println("Znalazłem: " + credentialsRepository.findByLogin("login123").toString());
		
		
		return "index";
	}
	
}
