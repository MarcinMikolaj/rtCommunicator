package project.rtc.registration.controllers.impl;

import org.springframework.stereotype.Controller;
import project.rtc.registration.controllers.RegistrationController;

@Controller
public class RegistrationControllerImpl implements RegistrationController {

	@Override
	public String getRegisterPage() {
		return "RegisterPage";
	}

}
