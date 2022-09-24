package project.rtc.registration;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class RegistrationController {
	
	@RequestMapping(path = "/app/register", method = RequestMethod.GET)
	public String getRegisterPage() {
		return "RegisterPage";
	}

}
