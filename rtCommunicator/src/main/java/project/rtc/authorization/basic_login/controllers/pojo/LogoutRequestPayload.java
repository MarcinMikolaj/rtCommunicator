package project.rtc.authorization.basic_login.controllers.pojo;

public class LogoutRequest {
	
	private boolean logout;
	
	public LogoutRequest() {}

	public LogoutRequest(boolean logout) {
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
