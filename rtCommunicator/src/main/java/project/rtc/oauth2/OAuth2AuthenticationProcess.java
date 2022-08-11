package project.rtc.oauth2;

import org.springframework.security.core.AuthenticationException;

public class OAuth2AuthenticationProcess extends AuthenticationException {

	private static final long serialVersionUID = 5127984993262579821L;

	public OAuth2AuthenticationProcess(String msg) {
		super(msg);
	}

}
