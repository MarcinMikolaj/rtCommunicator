package project.rtc.communicator.room.dto;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class Statement<A> {

	private final String action;
	private final String message;
	private final StatementType type;

	public Statement(A action, String message, StatementType type) {
		this.action = action.toString();
		this.message = message;
		this.type = type;
	}

}
