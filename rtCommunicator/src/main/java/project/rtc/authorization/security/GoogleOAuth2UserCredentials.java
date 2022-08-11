package project.rtc.authorization.security;

public class GoogleOAuth2UserCredentials {
	
	private String email;
	private String name;
	private String picture;
	private String sub;
	
	public GoogleOAuth2UserCredentials() {
		super();
	}

	public GoogleOAuth2UserCredentials(String email, String name, String picture, String sub) {
		super();
		this.email = email;
		this.name = name;
		this.picture = picture;
		this.sub = sub;
	}

    
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPicture() {
		return picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}

	public String getSub() {
		return sub;
	}

	public void setSub(String sub) {
		this.sub = sub;
	}

	@Override
	public String toString() {
		return "GoogleOAuth2UserCredentials [email=" + email + ", name=" + name + ", picture=" + picture + ", sub="
				+ sub + "]";
	}
	
}
