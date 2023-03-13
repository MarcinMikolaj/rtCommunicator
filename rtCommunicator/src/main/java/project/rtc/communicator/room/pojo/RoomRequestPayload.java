package project.rtc.communicator.room.pojo;

import lombok.Data;
import lombok.NoArgsConstructor;
import project.rtc.communicator.room.RoomAction;

@Data
@NoArgsConstructor
public class RoomRequestPayload {
	
	private RoomAction action;
	private String roomId;
	private String userNick;
	private String roomName;
	
}
