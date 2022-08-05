package project.rtc;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class LoginPageController {
	
	@RequestMapping(path = "/app/login")
	public String get() {
		return "index";
	}

}
