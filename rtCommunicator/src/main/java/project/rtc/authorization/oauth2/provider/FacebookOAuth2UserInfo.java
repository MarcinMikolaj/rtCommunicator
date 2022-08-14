package project.rtc.authorization.oauth2.provider;

import java.util.Map;

public class FacebookOAuth2UserInfo extends OAuth2UserInfo {

	public FacebookOAuth2UserInfo(Map<String, Object> attributes) {
		super(attributes);
	}
	
	@Override
	public String getId() {
		return (String) this.attributes.get("id");
	}

	@Override
	public String getName() {
		return (String) this.attributes.get("name");
	}

	@Override
	public String getEmail() {
		return (String) this.attributes.get("email");
	}

	@Override
	public String getImageUrl() {
		return (String) this.attributes.get("picture");
	}

}
