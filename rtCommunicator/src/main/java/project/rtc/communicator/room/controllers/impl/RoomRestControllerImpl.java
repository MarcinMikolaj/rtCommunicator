package project.rtc.communicator.room.controllers.impl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import project.rtc.communicator.room.controllers.RoomRestController;
import project.rtc.communicator.room.dto.RoomRequestPayload;
import project.rtc.communicator.room.dto.RoomResponsePayload;
import project.rtc.communicator.room.response_service.RoomResponseService;

// Is responsible for handling requests regarding room management in the application.
@RestController
@RequiredArgsConstructor
public class RoomRestControllerImpl implements RoomRestController {
	
	private final RoomResponseService roomService;


	@Override
	@PostMapping(path = "/app/rtc/room/create")
	public ResponseEntity<RoomResponsePayload> createRoomWithAuthoringUser(@RequestBody RoomRequestPayload roomRequest, HttpServletRequest httpServletRequest) throws ServletException {
		
		return new ResponseEntity<RoomResponsePayload>(roomService.createRoomWithAuthoringUser(httpServletRequest, roomRequest), HttpStatus.OK);
	 
	}


	@Override
	@PostMapping(path = "/app/rtc/room/user/add")
	public ResponseEntity<RoomResponsePayload> addNewUserToRoom(@RequestBody RoomRequestPayload roomRequest, HttpServletRequest httpServletRequest) {
		
		return new ResponseEntity<RoomResponsePayload>(roomService.addUserToRoom(httpServletRequest, roomRequest), HttpStatus.OK);
	
	}


	@Override
	@PostMapping(path = "/app/rtc/room/get")
	public ResponseEntity<RoomResponsePayload> getRooms(@RequestBody RoomRequestPayload roomRequest, HttpServletRequest httpServletRequest) throws ServletException {
		
		return new ResponseEntity<RoomResponsePayload>(roomService.getUserRooms(httpServletRequest, roomRequest), HttpStatus.OK);
	 
	}


	@Override
	@PostMapping(path = "/app/rtc/room/remove")
	public ResponseEntity<RoomResponsePayload> removeRoom(@RequestBody RoomRequestPayload roomRequest, HttpServletRequest httpServletRequest) throws ServletException {
		
		return new ResponseEntity<RoomResponsePayload>(roomService.remove(httpServletRequest, roomRequest), HttpStatus.OK);
	 
	}


	@Override
	@PostMapping(path = "/app/rtc/room/name/update")
	public ResponseEntity<RoomResponsePayload> renameRoom(@RequestBody RoomRequestPayload roomRequest, HttpServletRequest httpServletRequest) throws ServletException {
		
		return new ResponseEntity<RoomResponsePayload>(roomService.renameRoom(httpServletRequest, roomRequest), HttpStatus.OK);
	
	}


	@Override
	@PostMapping(path = "/app/rtc/room/user/remove")
	public ResponseEntity<RoomResponsePayload> removeUserFromRoom(@RequestBody RoomRequestPayload roomRequest, HttpServletRequest httpServletRequest) throws ServletException {
		
		return new ResponseEntity<RoomResponsePayload>(roomService.removeUserFromRoom(httpServletRequest, roomRequest), HttpStatus.OK);	
	 
	}


	@Override
	@PostMapping(path = "/app/rtc/room/user/leave")
	public ResponseEntity<RoomResponsePayload> leaveRoom(@RequestBody RoomRequestPayload roomRequest, HttpServletRequest httpServletRequest) throws ServletException {
		
		return new ResponseEntity<RoomResponsePayload>(roomService.leaveRoom(httpServletRequest, roomRequest), HttpStatus.OK);
	
	}
	
}
