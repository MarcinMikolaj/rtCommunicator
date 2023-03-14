package project.rtc.communicator.room.dto;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class RoomResponsePayload {

	private boolean success;

	private RoomAction action;

	@ToString.Exclude
	private List<Room> rooms = new ArrayList<Room>();

	@ToString.Exclude
	private Map<String, Integer> unreadMessages = new HashMap<String, Integer>();

	private List<Statement<RoomAction>> statements = new ArrayList<Statement<RoomAction>>();

	public RoomResponsePayload(RoomAction action) {
		super();
		this.success = false;
		this.action = action;
	}

}
