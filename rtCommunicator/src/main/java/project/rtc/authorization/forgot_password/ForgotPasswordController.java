package project.rtc.authorization.forgot_password;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class ForgotPasswordController {
	
	@RequestMapping(path = "/app/forgot", method = RequestMethod.GET)
	public String get(){
		return "RequestToChangePasswordForm";
	}
	
	@RequestMapping(path = "/app/forgot/password/tk", method = RequestMethod.GET)
	public String handleChangingPassword() {
		return "EnterNewPasswordForm";
		
	}
	
}
