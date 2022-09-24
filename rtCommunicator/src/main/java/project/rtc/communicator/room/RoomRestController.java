package project.rtc.communicator.room;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import project.rtc.communicator.room.pojo.RoomRequestPayload;
import project.rtc.communicator.room.pojo.RoomResponsePayload;

@RestController
public class RoomRestController {
	
	private RoomResponseService roomService;
	
	public RoomRestController(RoomResponseServiceImpl roomServiceImpl) {
		this.roomService = roomServiceImpl;
	}
	
	@RequestMapping(path = "/app/rtc/room/create", method = RequestMethod.POST)
	public ResponseEntity<RoomResponsePayload> createRoomWithAuthoringUser(@RequestBody RoomRequestPayload roomRequest, HttpServletRequest httpServletRequest) throws ServletException{
		RoomResponsePayload roomResponse = roomService.createRoomWithAuthoringUser(httpServletRequest, roomRequest);
		return new ResponseEntity<RoomResponsePayload>(roomResponse, HttpStatus.OK);
	 }
	
	@RequestMapping(path = "/app/rtc/room/create/with/user", method = RequestMethod.POST)
	public ResponseEntity<RoomResponsePayload> createRoomWithFriend(@RequestBody RoomRequestPayload roomRequest, HttpServletRequest httpServletRequest) throws ServletException{
		RoomResponsePayload roomResponse = roomService.createRoomWithFriend(httpServletRequest, roomRequest);
		return new ResponseEntity<RoomResponsePayload>(roomResponse, HttpStatus.OK);
	 }
	
	
	@RequestMapping(path = "/app/rtc/room/get", method = RequestMethod.POST)
	public ResponseEntity<RoomResponsePayload> getRooms(@RequestBody RoomRequestPayload roomRequest, HttpServletRequest httpServletRequest) throws ServletException{
		RoomResponsePayload roomResponse = roomService.getUserRooms(httpServletRequest, roomRequest);
		return new ResponseEntity<RoomResponsePayload>(roomResponse, HttpStatus.OK);
	 }
	
	
	@RequestMapping(path = "/app/rtc/room/remove", method = RequestMethod.POST)
	public ResponseEntity<RoomResponsePayload> removeRoom(@RequestBody RoomRequestPayload roomRequest, HttpServletRequest httpServletRequest) throws ServletException{
		RoomResponsePayload roomResponse = roomService.remove(httpServletRequest, roomRequest);
		return new ResponseEntity<RoomResponsePayload>(roomResponse, HttpStatus.OK);
	 }
	
	@RequestMapping(path = "/app/rtc/room/user/add", method = RequestMethod.POST)
	public ResponseEntity<RoomResponsePayload> addUserToRoom(@RequestBody RoomRequestPayload roomRequest, HttpServletRequest httpServletRequest) throws ServletException{
		RoomResponsePayload roomResponse = roomService.addUserToRoom(httpServletRequest, roomRequest);
		return new ResponseEntity<RoomResponsePayload>(roomResponse, HttpStatus.OK);
	 }
	
	@RequestMapping(path = "/app/rtc/room/name/update", method = RequestMethod.POST)
	public ResponseEntity<RoomResponsePayload> renameRoom(@RequestBody RoomRequestPayload roomRequest, HttpServletRequest httpServletRequest) throws ServletException{
		RoomResponsePayload roomResponse = roomService.renameRoom(httpServletRequest, roomRequest);
		return new ResponseEntity<RoomResponsePayload>(roomResponse, HttpStatus.OK);
	 }
	
	
	@RequestMapping(path = "/app/rtc/room/user/remove", method = RequestMethod.POST)
	public ResponseEntity<RoomResponsePayload> removeUserFromRoom(@RequestBody RoomRequestPayload roomRequest, HttpServletRequest httpServletRequest) throws ServletException{
		RoomResponsePayload roomResponse = roomService.removeUserFromRoom(httpServletRequest, roomRequest);
		return new ResponseEntity<RoomResponsePayload>(roomResponse, HttpStatus.OK);
	 }
	
	@RequestMapping(path = "/app/rtc/room/user/leave", method = RequestMethod.POST)
	public ResponseEntity<RoomResponsePayload> leaveRoom(@RequestBody RoomRequestPayload roomRequest, HttpServletRequest httpServletRequest) throws ServletException{
		RoomResponsePayload roomResponse = roomService.leaveRoom(httpServletRequest, roomRequest);
		return new ResponseEntity<RoomResponsePayload>(roomResponse, HttpStatus.OK);
	 }
	
}
