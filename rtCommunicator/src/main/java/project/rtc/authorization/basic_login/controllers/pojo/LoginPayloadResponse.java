package project.rtc.authorization.basic_login.controllers.pojo;

public final class LoginPayloadResponse {
	
	private final String jwtToken;
	private final String tokenType;
	
	public LoginPayloadResponse(String jwtToken) {
		super();
		this.jwtToken = jwtToken;
		this.tokenType = "Bearer";
	}
	
	
	public LoginPayloadResponse(String jwtToken, String tokenType) {
		super();
		this.jwtToken = jwtToken;
		this.tokenType = tokenType;
	}


	public String getTokenType() {
		return tokenType;
	}

	
	public String getJwtToken() {
		return jwtToken;
	}

	@Override
	public String toString() {
		return "LoginPayloadResponse [jwtToken=" + jwtToken + "]";
	}
	
}
