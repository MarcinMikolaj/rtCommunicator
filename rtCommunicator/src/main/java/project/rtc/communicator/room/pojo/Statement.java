package project.rtc.communicator.room.pojo;

public class Statement<A> {
	
	private final StatementType type;
	private final String action;
	private final String message;
	
	public Statement(A action, String message, StatementType type) {
		this.action = action.toString();
		this.message = message;
		this.type = type;
	}

	public StatementType getType() {
		return type;
	}

	public String getAction() {
		return action;
	}

	public String getMessage() {
		return message;
	}


	@Override
	public String toString() {
		return "Statement [type=" + type + ", action=" + action + ", message=" + message + "]";
	}
	
}
