package project.rtc.authorization.forgot_password.controllers.impl;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import project.rtc.authorization.forgot_password.controllers.ForgotPasswordController;

@Controller
public class ForgotPasswordControllerImpl implements ForgotPasswordController {

	@Override
	@RequestMapping(path = "/app/forgot", method = RequestMethod.GET)
	public String get(){
		return "RequestToChangePasswordForm";
	}

	@Override
	@RequestMapping(path = "/app/forgot/password/tk", method = RequestMethod.GET)
	public String handleChangingPassword() {
		return "EnterNewPasswordForm";
		
	}
	
}
