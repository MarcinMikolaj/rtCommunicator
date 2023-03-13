package project.rtc.communicator.room.response_service;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import project.rtc.communicator.room.pojo.RoomRequestPayload;
import project.rtc.communicator.room.pojo.RoomResponsePayload;

public interface RoomResponseService {
	
	public RoomResponsePayload createRoomWithAuthoringUser(HttpServletRequest httpServletRequest, RoomRequestPayload roomRequest) throws ServletException;
//	public RoomResponsePayload createRoomWithFriend(HttpServletRequest httpServletRequest, RoomRequestPayload roomRequest);
	
	public RoomResponsePayload getUserRooms(HttpServletRequest httpServletRequest, RoomRequestPayload roomRequest) throws ServletException;
	public RoomResponsePayload remove(HttpServletRequest httpServletRequest, RoomRequestPayload roomRequest) throws ServletException;
	public RoomResponsePayload renameRoom(HttpServletRequest httpServletRequest, RoomRequestPayload roomRequest);
	public RoomResponsePayload addUserToRoom(HttpServletRequest httpServletRequest, RoomRequestPayload roomRequest);
	public RoomResponsePayload removeUserFromRoom(HttpServletRequest httpServletRequest, RoomRequestPayload roomRequest);
	public RoomResponsePayload leaveRoom(HttpServletRequest httpServletRequest, RoomRequestPayload roomRequest) throws ServletException;
	
}
