package project.rtc.authorization.forgot_password;

public class ChangePasswordRequest {
	
	private String token;
	private String password;
	
	public ChangePasswordRequest() {
		super();
	}

	public ChangePasswordRequest(String token, String password) {
		super();
		this.token = token;
		this.password = password;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}


	@Override
	public String toString() {
		return "ChangePasswordRequest [token=" + token + ", password=" + password + "]";
	}

}
