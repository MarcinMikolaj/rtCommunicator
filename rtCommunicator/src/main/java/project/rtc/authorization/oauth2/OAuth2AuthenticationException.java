package project.rtc.authorization.oauth2;

import org.springframework.security.core.AuthenticationException;

public class OAuth2AuthenticationException extends AuthenticationException {

	private static final long serialVersionUID = 5127984993262579821L;

	public OAuth2AuthenticationException(String msg) {
		super(msg);
	}

}
