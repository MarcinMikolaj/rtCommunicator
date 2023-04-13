package project.rtc.authorization.basic_login.dto;

import lombok.Data;

@Data
public final class LoginRequestPayload {
	
	private final String email;
	private final String password;
	private final boolean remember_me;

}
