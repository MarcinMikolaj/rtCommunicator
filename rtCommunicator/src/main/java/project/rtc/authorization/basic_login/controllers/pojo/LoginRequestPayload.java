package project.rtc.authorization.basic_login.controllers.pojo;

public final class LoginPayloadRequest {
	
	private final String email;
	private final String password;
	private final boolean remember_me;
	
	public LoginPayloadRequest(String email, String password, boolean remember_me) {
		super();
		this.email = email;
		this.password = password;
		this.remember_me = remember_me;
	}

	public String getEmail() {
		return email;
	}

	public String getPassword() {
		return password;
	}
	
	
	public boolean isRemember_me() {
		return remember_me;
	}

	@Override
	public String toString() {
		return "LoginPayloadRequest [email=" + email + ", password=" + password + ", remember_me=" + remember_me + "]";
	}

}
