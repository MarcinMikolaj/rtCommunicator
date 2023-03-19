package project.rtc.communicator.user.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import project.rtc.communicator.room.dto.Statement;

@Getter
@Setter
@ToString
public class UserResponseBody {
	
	private boolean success;
	private UserAction action;
	private User user;
	private List<Statement<UserAction>> statements = new ArrayList<Statement<UserAction>>();
	

	public UserResponseBody() {
		super();
		this.success = false;
	}

	public UserResponseBody(boolean success, UserAction action) {
		super();
		this.success = success;
		this.action = action;
	}
	
}
