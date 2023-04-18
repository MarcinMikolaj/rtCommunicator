package project.rtc.authorization.basic_login.controllers.mvc.impl;

import org.springframework.stereotype.Controller;
import project.rtc.authorization.basic_login.controllers.mvc.AuthenticationPageController;

@Controller
public class AuthenticationPageControllerImpl implements AuthenticationPageController {

	@Override
	public String get() {
		return "LoginPage";
	}
	
}
