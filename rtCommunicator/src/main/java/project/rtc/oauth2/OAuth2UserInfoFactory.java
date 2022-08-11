package project.rtc.oauth2;

import java.util.Map;

import project.rtc.ConsoleColors;

public class OAuth2UserInfoFactory {
	
	public static OAuth2UserInfo getOAuth2UserInfo(String registrationId, Map<String, Object> attributes) {
		
		if(registrationId.equalsIgnoreCase(project.rtc.oauth2.AuthProvider.facebook.toString())) {
			return new FacebookOAuth2UserInfo(attributes);
		} else if (registrationId.equalsIgnoreCase(AuthProvider.google.toString())) {
			return new GoogleOAuth2UserInfo(attributes);
		} else {
			throw new OAuth2AuthenticationProcess(
					ConsoleColors.RED +
					"Sign in by: " + registrationId.toString() + " is not allowed yet !" +
					ConsoleColors.RESET
					);
		}
		
	}

}
