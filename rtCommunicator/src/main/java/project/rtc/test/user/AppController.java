package project.rtc.test.user;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class AppController {
	
	@RequestMapping(path = "/app/panel", method = RequestMethod.GET)
	public String getApp() {
		return "AppPage";
	}

}
