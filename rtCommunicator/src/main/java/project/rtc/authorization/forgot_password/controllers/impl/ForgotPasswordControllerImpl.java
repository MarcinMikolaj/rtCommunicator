package project.rtc.authorization.forgot_password.controllers.impl;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import project.rtc.authorization.forgot_password.controllers.ForgotPasswordController;

@Controller
public class ForgotPasswordControllerImpl implements ForgotPasswordController {

	@Override
	@GetMapping(path = "/app/forgot")
	public String get(){
		return "RequestToChangePasswordForm";
	}

	@Override
	@GetMapping(path = "/app/forgot/password/tk")
	public String handleChangingPassword() {
		return "EnterNewPasswordForm";
		
	}
	
}
