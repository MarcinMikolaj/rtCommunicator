package project.rtc.communicator.room;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import project.rtc.communicator.room.pojo.RoomRequest;
import project.rtc.communicator.room.pojo.RoomResponse;

public interface RoomService {
	
	public RoomResponse createRoomWithAuthoringUser(HttpServletRequest httpServletRequest, RoomRequest roomRequest) throws ServletException;
	public RoomResponse createRoomWithFriend(HttpServletRequest httpServletRequest, RoomRequest roomRequest);
	public RoomResponse getUserRooms(HttpServletRequest httpServletRequest, RoomRequest roomRequest) throws ServletException;
	public RoomResponse remove(HttpServletRequest httpServletRequest, RoomRequest roomRequest) throws ServletException;
	public RoomResponse renameRoom(HttpServletRequest httpServletRequest, RoomRequest roomRequest);
	public RoomResponse addUserToRoom(HttpServletRequest httpServletRequest, RoomRequest roomRequest);
	public RoomResponse removeUserFromRoom(HttpServletRequest httpServletRequest, RoomRequest roomRequest);
	public RoomResponse leaveRoom(HttpServletRequest httpServletRequest, RoomRequest roomRequest) throws ServletException;
	
}
