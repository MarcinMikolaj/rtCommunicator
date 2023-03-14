package project.rtc.registration.controllers.impl;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import project.rtc.registration.controllers.RegistrationController;

@Controller
public class RegistrationControllerImpl implements RegistrationController {

	@Override
	@RequestMapping(path = "/app/register", method = RequestMethod.GET)
	public String getRegisterPage() {
		return "RegisterPage";
	}

}
