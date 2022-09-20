package project.rtc.communicator.room;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import project.rtc.communicator.room.pojo.RoomRequest;
import project.rtc.communicator.room.pojo.RoomResponse;

@RestController
public class RoomRestController {
	
	private RoomService roomService;
	
	public RoomRestController(RoomServiceImpl roomServiceImpl) {
		this.roomService = roomServiceImpl;
	}
	
	@RequestMapping(path = "/app/rtc/room/create", method = RequestMethod.POST)
	public ResponseEntity<RoomResponse> createRoomWithAuthoringUser(@RequestBody RoomRequest roomRequest, HttpServletRequest httpServletRequest) throws ServletException{
		RoomResponse roomResponse = roomService.createRoomWithAuthoringUser(httpServletRequest, roomRequest);
		return new ResponseEntity<RoomResponse>(roomResponse, HttpStatus.OK);
	 }
	
	@RequestMapping(path = "/app/rtc/room/create/with/user", method = RequestMethod.POST)
	public ResponseEntity<RoomResponse> createRoomWithFriend(@RequestBody RoomRequest roomRequest, HttpServletRequest httpServletRequest) throws ServletException{
		RoomResponse roomResponse = roomService.createRoomWithFriend(httpServletRequest, roomRequest);
		return new ResponseEntity<RoomResponse>(roomResponse, HttpStatus.OK);
	 }
	
	
	@RequestMapping(path = "/app/rtc/room/get", method = RequestMethod.POST)
	public ResponseEntity<RoomResponse> getRooms(@RequestBody RoomRequest roomRequest, HttpServletRequest httpServletRequest) throws ServletException{
		RoomResponse roomResponse = roomService.getUserRooms(httpServletRequest, roomRequest);
		return new ResponseEntity<RoomResponse>(roomResponse, HttpStatus.OK);
	 }
	
	
	@RequestMapping(path = "/app/rtc/room/remove", method = RequestMethod.POST)
	public ResponseEntity<RoomResponse> removeRoom(@RequestBody RoomRequest roomRequest, HttpServletRequest httpServletRequest) throws ServletException{
		RoomResponse roomResponse = roomService.remove(httpServletRequest, roomRequest);
		return new ResponseEntity<RoomResponse>(roomResponse, HttpStatus.OK);
	 }
	
	@RequestMapping(path = "/app/rtc/room/user/add", method = RequestMethod.POST)
	public ResponseEntity<RoomResponse> addUserToRoom(@RequestBody RoomRequest roomRequest, HttpServletRequest httpServletRequest) throws ServletException{
		RoomResponse roomResponse = roomService.addUserToRoom(httpServletRequest, roomRequest);
		return new ResponseEntity<RoomResponse>(roomResponse, HttpStatus.OK);
	 }
	
	@RequestMapping(path = "/app/rtc/room/name/update", method = RequestMethod.POST)
	public ResponseEntity<RoomResponse> renameRoom(@RequestBody RoomRequest roomRequest, HttpServletRequest httpServletRequest) throws ServletException{
		RoomResponse roomResponse = roomService.renameRoom(httpServletRequest, roomRequest);
		return new ResponseEntity<RoomResponse>(roomResponse, HttpStatus.OK);
	 }
	
	
	@RequestMapping(path = "/app/rtc/room/user/remove", method = RequestMethod.POST)
	public ResponseEntity<RoomResponse> removeUserFromRoom(@RequestBody RoomRequest roomRequest, HttpServletRequest httpServletRequest) throws ServletException{
		RoomResponse roomResponse = roomService.removeUserFromRoom(httpServletRequest, roomRequest);
		return new ResponseEntity<RoomResponse>(roomResponse, HttpStatus.OK);
	 }
	
	@RequestMapping(path = "/app/rtc/room/user/leave", method = RequestMethod.POST)
	public ResponseEntity<RoomResponse> leaveRoom(@RequestBody RoomRequest roomRequest, HttpServletRequest httpServletRequest) throws ServletException{
		RoomResponse roomResponse = roomService.leaveRoom(httpServletRequest, roomRequest);
		return new ResponseEntity<RoomResponse>(roomResponse, HttpStatus.OK);
	 }
	
}
