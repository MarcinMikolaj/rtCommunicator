package project.rtc.communicator.room.pojo;

import java.util.ArrayList;
import java.util.List;

import project.rtc.communicator.room.Room;
import project.rtc.communicator.room.RoomAction;
import project.rtc.communicator.room.Statement;

public class RoomResponsePayload {
	
	private boolean success;
	private RoomAction action;
	private List<Room> rooms = new ArrayList<Room>();
	private List<Statement<RoomAction>> statements = new ArrayList<Statement<RoomAction>>();
	
	public RoomResponsePayload() {
		super();
	}

	public RoomResponsePayload(boolean success, RoomAction action) {
		super();
		this.success = success;
		this.action = action;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public RoomAction getAction() {
		return action;
	}

	public void setAction(RoomAction action) {
		this.action = action;
	}

	public List<Room> getRooms() {
		return rooms;
	}

	public void setRooms(List<Room> rooms) {
		this.rooms = rooms;
	}

	
	public List<Statement<RoomAction>> getStatements() {
		return statements;
	}

	public void setStatements(List<Statement<RoomAction>> statements) {
		this.statements = statements;
	}

	@Override
	public String toString() {
		return "RoomResponse [success=" + success + ", action=" + action + ", rooms=" + rooms + ", statements="
				+ statements + "]";
	}

}
