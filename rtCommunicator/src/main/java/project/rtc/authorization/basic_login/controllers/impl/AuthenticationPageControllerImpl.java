package project.rtc.authorization.basic_login.controllers.impl;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import project.rtc.authorization.basic_login.controllers.AuthenticationPageController;

@Controller
public class AuthenticationPageControllerImpl implements AuthenticationPageController {

	@Override
	@RequestMapping(path = "/app/login")
	public String get() {
		return "LoginPage";
	}
	
}
