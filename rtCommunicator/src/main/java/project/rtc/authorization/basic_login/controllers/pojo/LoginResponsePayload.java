package project.rtc.authorization.basic_login.controllers.pojo;

public final class LoginResponsePayload {
	
	private final boolean isAuthenticated;
	private final String jwtToken;
	private final String tokenType;
	
	public LoginResponsePayload(String jwtToken, boolean isAuthenticated) {
		super();
		this.jwtToken = jwtToken;
		this.tokenType = "Bearer";
		this.isAuthenticated = isAuthenticated;
	}
	
	
	public boolean isAuthenticated() {
		return isAuthenticated;
	}


	public String getTokenType() {
		return tokenType;
	}

	
	public String getJwtToken() {
		return jwtToken;
	}

	@Override
	public String toString() {
		return "LoginResponsePayload [isAuthenticated=" + isAuthenticated + ", jwtToken=" + jwtToken + ", tokenType="
				+ tokenType + "]";
	}	
	
}
