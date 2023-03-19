package project.rtc.authorization.forgot_password.controllers.impl;

import org.springframework.stereotype.Controller;
import project.rtc.authorization.forgot_password.controllers.ForgotPasswordController;

@Controller

public class ForgotPasswordControllerImpl implements ForgotPasswordController {

	@Override
	public String get(){
		return "RequestToChangePasswordForm";
	}

	@Override
	public String handleChangingPassword() {return "EnterNewPasswordForm";}
	
}
