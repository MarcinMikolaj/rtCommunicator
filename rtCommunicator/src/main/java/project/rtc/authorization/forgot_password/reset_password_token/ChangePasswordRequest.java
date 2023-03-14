package project.rtc.authorization.forgot_password.reset_password_token;

import lombok.Data;

@Data
public class ChangePasswordRequest {
	
	private String token;
	private String password;

}
