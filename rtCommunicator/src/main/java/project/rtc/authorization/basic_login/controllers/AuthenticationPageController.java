package project.rtc.authorization.basic_login.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class AuthenticationPageController {
	
	@RequestMapping(path = "/app/login")
	public String get() {
		return "LoginPage";
	}
	
}
