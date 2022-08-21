package project.rtc.authorization.controllers;

public final class LoginPayloadRequest {
	
	private final String email;
	private final String password;
	
	public LoginPayloadRequest(String email, String password) {
		super();
		this.email = email;
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public String getPassword() {
		return password;
	}

	
	@Override
	public String toString() {
		return "LoginPayloadRequest [email=" + email + ", password=" + password + "]";
	}
	
}
