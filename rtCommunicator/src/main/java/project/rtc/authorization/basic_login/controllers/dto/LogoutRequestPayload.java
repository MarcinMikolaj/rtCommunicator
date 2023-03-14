package project.rtc.authorization.basic_login.controllers.dto;

public class LogoutRequestPayload {
	
	private  boolean logout;
	
	public LogoutRequestPayload() {
		super();
	}

	public LogoutRequestPayload(boolean logout) {
		super();
		this.logout = logout;
	}

	public boolean isLogout() {
		return logout;
	}
	
	public void setLogout(boolean logout) {
		this.logout = logout;
	}

	@Override
	public String toString() {
		return "LogoutRequest [logout=" + logout + "]";
	}
}
