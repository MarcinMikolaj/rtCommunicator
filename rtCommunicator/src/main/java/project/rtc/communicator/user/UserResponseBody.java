package project.rtc.communicator.user;

import java.util.ArrayList;
import java.util.List;

import project.rtc.communicator.room.pojo.Statement;

public class UserResponseBody {
	
	private boolean success;
	private UserAction action;
	
	private User user;
	private List<Statement<UserAction>> statements = new ArrayList<Statement<UserAction>>();
	
	
	public UserResponseBody() {
		super();
		this.success = false;
	}

	
	public UserResponseBody(boolean success, UserAction action, User user, List<Statement<UserAction>> statements) {
		super();
		this.success = success;
		this.action = action;
		this.user = user;
		this.statements = statements;
	}


	public boolean isSuccess() {
		return success;
	}


	public void setSuccess(boolean success) {
		this.success = success;
	}


	public UserAction getAction() {
		return action;
	}


	public void setAction(UserAction action) {
		this.action = action;
	}


	public User getUser() {
		return user;
	}


	public void setUser(User user) {
		this.user = user;
	}


	public List<Statement<UserAction>> getStatements() {
		return statements;
	}


	public void setStatements(List<Statement<UserAction>> statements) {
		this.statements = statements;
	}


	@Override
	public String toString() {
		return "UserResponseBody [success=" + success + ", action=" + action + ", user=" + user + ", statements="
				+ statements + "]";
	}
	
}
