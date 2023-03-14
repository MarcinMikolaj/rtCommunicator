package project.rtc.registration.controllers.impl;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import project.rtc.registration.controllers.RegistrationController;

@Controller
public class RegistrationControllerImpl implements RegistrationController {

	@Override
	@GetMapping(path = "/app/register")
	public String getRegisterPage() {
		return "RegisterPage";
	}

}
