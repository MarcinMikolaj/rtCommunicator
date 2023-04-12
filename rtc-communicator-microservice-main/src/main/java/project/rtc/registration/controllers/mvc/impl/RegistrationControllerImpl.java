package project.rtc.registration.controllers.mvc.impl;

import org.springframework.stereotype.Controller;
import project.rtc.registration.controllers.mvc.RegistrationController;

@Controller
public class RegistrationControllerImpl implements RegistrationController {

	@Override
	public String getRegisterPage() {
		return "RegisterPage";
	}

}
