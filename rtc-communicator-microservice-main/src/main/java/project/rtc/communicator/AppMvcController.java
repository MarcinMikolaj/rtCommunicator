package project.rtc.communicator;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AppMvcController {
	@GetMapping(path = "/app/panel")
	public String getApp() {
		return "AppPage";
	}

}
