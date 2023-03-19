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

@RestController
@RequiredArgsConstructor
public class RoomRestControllerImpl implements RoomRestController {
	
	private final RoomResponseService roomService;

	@Override
	public ResponseEntity<RoomResponsePayload> createRoomWithAuthoringUser(@RequestBody RoomRequestPayload roomRequest
			, HttpServletRequest httpServletRequest) throws ServletException {
		return new ResponseEntity<>(roomService.createRoomWithAuthoringUser(httpServletRequest, roomRequest), HttpStatus.OK);
	}

	@Override
	public ResponseEntity<RoomResponsePayload> addNewUserToRoom(@RequestBody RoomRequestPayload roomRequest
			, HttpServletRequest httpServletRequest) {
		return new ResponseEntity<>(roomService.addUserToRoom(httpServletRequest, roomRequest), HttpStatus.OK);
	}

	@Override
	public ResponseEntity<RoomResponsePayload> getRooms(@RequestBody RoomRequestPayload roomRequest
			, HttpServletRequest httpServletRequest) throws ServletException {
		return new ResponseEntity<>(roomService.getUserRooms(httpServletRequest, roomRequest), HttpStatus.OK);
	}

	@Override
	public ResponseEntity<RoomResponsePayload> removeRoom(@RequestBody RoomRequestPayload roomRequest
			, HttpServletRequest httpServletRequest) throws ServletException {
		return new ResponseEntity<>(roomService.remove(httpServletRequest, roomRequest), HttpStatus.OK);
	}

	@Override
	public ResponseEntity<RoomResponsePayload> renameRoom(@RequestBody RoomRequestPayload roomRequest
			, HttpServletRequest httpServletRequest) throws ServletException {
		return new ResponseEntity<>(roomService.renameRoom(httpServletRequest, roomRequest), HttpStatus.OK);
	}

	@Override
	public ResponseEntity<RoomResponsePayload> removeUserFromRoom(@RequestBody RoomRequestPayload roomRequest
			, HttpServletRequest httpServletRequest) throws ServletException {
		return new ResponseEntity<>(roomService.removeUserFromRoom(httpServletRequest, roomRequest), HttpStatus.OK);
	}

	@Override
	public ResponseEntity<RoomResponsePayload> leaveRoom(@RequestBody RoomRequestPayload roomRequest
			, HttpServletRequest httpServletRequest) throws ServletException {
		return new ResponseEntity<>(roomService.leaveRoom(httpServletRequest, roomRequest), HttpStatus.OK);
	}
	
}
