package project.rtc.communicator.room.controllers.impl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import project.rtc.communicator.room.controllers.RoomRestController;
import project.rtc.communicator.room.dto.RoomRequestPayload;
import project.rtc.communicator.room.dto.RoomResponsePayload;
import project.rtc.communicator.room.response_service.RoomResponseService;
import project.rtc.communicator.room.response_service.impl.RoomResponseServiceImpl;

// Is responsible for handling requests regarding room management in the application.
@RestController
public class RoomRestControllerImpl implements RoomRestController {
	
	private RoomResponseService roomService;

	public RoomRestControllerImpl(RoomResponseServiceImpl roomServiceImpl) {
		this.roomService = roomServiceImpl;
		
	}

	@Override
	@RequestMapping(path = "/app/rtc/room/create", method = RequestMethod.POST)
	public ResponseEntity<RoomResponsePayload> createRoomWithAuthoringUser(@RequestBody RoomRequestPayload roomRequest, HttpServletRequest httpServletRequest) throws ServletException {
		
		return new ResponseEntity<RoomResponsePayload>(roomService.createRoomWithAuthoringUser(httpServletRequest, roomRequest), HttpStatus.OK);
	 
	}


	@Override
	@RequestMapping(path = "/app/rtc/room/user/add", method = RequestMethod.POST)
	public ResponseEntity<RoomResponsePayload> addNewUserToRoom(@RequestBody RoomRequestPayload roomRequest, HttpServletRequest httpServletRequest) {
		
		return new ResponseEntity<RoomResponsePayload>(roomService.addUserToRoom(httpServletRequest, roomRequest), HttpStatus.OK);
	
	}


	@Override
	@RequestMapping(path = "/app/rtc/room/get", method = RequestMethod.POST)
	public ResponseEntity<RoomResponsePayload> getRooms(@RequestBody RoomRequestPayload roomRequest, HttpServletRequest httpServletRequest) throws ServletException {
		
		return new ResponseEntity<RoomResponsePayload>(roomService.getUserRooms(httpServletRequest, roomRequest), HttpStatus.OK);
	 
	}


	@Override
	@RequestMapping(path = "/app/rtc/room/remove", method = RequestMethod.POST)
	public ResponseEntity<RoomResponsePayload> removeRoom(@RequestBody RoomRequestPayload roomRequest, HttpServletRequest httpServletRequest) throws ServletException {
		
		return new ResponseEntity<RoomResponsePayload>(roomService.remove(httpServletRequest, roomRequest), HttpStatus.OK);
	 
	}


	@Override
	@RequestMapping(path = "/app/rtc/room/name/update", method = RequestMethod.POST)
	public ResponseEntity<RoomResponsePayload> renameRoom(@RequestBody RoomRequestPayload roomRequest, HttpServletRequest httpServletRequest) throws ServletException {
		
		return new ResponseEntity<RoomResponsePayload>(roomService.renameRoom(httpServletRequest, roomRequest), HttpStatus.OK);
	
	}


	@Override
	@RequestMapping(path = "/app/rtc/room/user/remove", method = RequestMethod.POST)
	public ResponseEntity<RoomResponsePayload> removeUserFromRoom(@RequestBody RoomRequestPayload roomRequest, HttpServletRequest httpServletRequest) throws ServletException {
		
		return new ResponseEntity<RoomResponsePayload>(roomService.removeUserFromRoom(httpServletRequest, roomRequest), HttpStatus.OK);	
	 
	}


	@Override
	@RequestMapping(path = "/app/rtc/room/user/leave", method = RequestMethod.POST)
	public ResponseEntity<RoomResponsePayload> leaveRoom(@RequestBody RoomRequestPayload roomRequest, HttpServletRequest httpServletRequest) throws ServletException {
		
		return new ResponseEntity<RoomResponsePayload>(roomService.leaveRoom(httpServletRequest, roomRequest), HttpStatus.OK);
	
	}
	
}
