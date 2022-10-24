package project.rtc.communicator.room.pojo;

public final class Statement<A> {
	
	private final A action;
	private final StatementType type;
	private final String message;
	
	public Statement(A action, String message, StatementType type) {
		super();
		this.action = action;
		this.message = message;
		this.type = type;
	}

	public A action() {
		return action;
	}

	public String getMessage() {
		return message;
	}
	
	public StatementType getType() {
		return type;
	}

	@Override
	public String toString() {
		return "Statement [action=" + action + ", type=" + type + ", message=" + message + "]";
	}

}
