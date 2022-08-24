package project.rtc.authorization.forgot_password;

public class ForgotPasswordRequest {
	
	private String email;
	
	public ForgotPasswordRequest() {}

	public ForgotPasswordRequest(String email) {
		super();
		this.email = email;
	}

	public String getEmail() {
		return email;
	}
	
	
	public void setEmail(String email) {
		this.email = email;
	}


	@Override
	public String toString() {
		return "ForgotPasswordRequest [email=" + email + "]";
	}
	
}
