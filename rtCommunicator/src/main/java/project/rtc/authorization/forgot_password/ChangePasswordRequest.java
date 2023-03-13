package project.rtc.authorization.forgot_password;

import lombok.Data;

@Data
public class ChangePasswordRequest {
	
	private String token;
	private String password;

}
