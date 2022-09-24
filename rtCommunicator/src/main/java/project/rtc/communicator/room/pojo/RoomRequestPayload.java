package project.rtc.communicator.room.pojo;

import project.rtc.communicator.room.RoomAction;

public class RoomRequestPayload {
	
	private RoomAction action;
	private String roomId;
	private String userNick;
	private String roomName;
	
	public RoomRequestPayload() {
	}

	
	public RoomRequestPayload(String userNick) {
		super();
		this.userNick = userNick;
	}

	
	public RoomAction getAction() {
		return action;
	}

	public void setAction(RoomAction action) {
		this.action = action;
	}


	public String getRoomId() {
		return roomId;
	}

	public void setRoomId(String roomId) {
		this.roomId = roomId;
	}

	public String getUserNick() {
		return userNick;
	}

	public void setUserNick(String userNick) {
		this.userNick = userNick;
	}
	
	
	public String getRoomName() {
		return roomName;
	}


	public void setRoomName(String roomName) {
		this.roomName = roomName;
	}


	@Override
	public String toString() {
		return "RoomRequest [action=" + action + ", roomId=" + roomId + ", userNick=" + userNick + "]";
	}
	
}
