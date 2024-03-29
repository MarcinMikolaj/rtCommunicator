package project.rtc.authorization.oauth2.provider;

import java.util.Map;

import project.rtc.authorization.oauth2.OAuth2AuthenticationException;
import project.rtc.infrastructure.utils.ConsoleColors;

public abstract class OAuth2UserInfoFactory {
	
	public static OAuth2UserInfo getOAuth2UserInfo(String registrationId, Map<String, Object> attributes) {
		
		if(registrationId.equalsIgnoreCase(project.rtc.authorization.oauth2.provider.AuthProvider.facebook.toString())) {
			return new FacebookOAuth2UserInfo(attributes);
		} else if (registrationId.equalsIgnoreCase(AuthProvider.google.toString())) {
			return new GoogleOAuth2UserInfo(attributes);
		} else {
			throw new OAuth2AuthenticationException(
					ConsoleColors.RED +
					"Authorization by: " + registrationId.toString() + " is not allowed yet !" +
					ConsoleColors.RESET
					);
		}
		
	}

}
