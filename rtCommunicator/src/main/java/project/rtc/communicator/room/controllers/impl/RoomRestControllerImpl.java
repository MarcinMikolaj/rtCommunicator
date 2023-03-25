package project.rtc.communicator.room.controllers.impl;

import javax.servlet.http.HttpServletRequest;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import project.rtc.infrastructure.exception.exceptions.RoomNotFoundException;
import project.rtc.communicator.room.controllers.RoomRestController;
import project.rtc.communicator.room.dto.RoomRequestDto;
import project.rtc.communicator.room.dto.RoomResponseDto;
import project.rtc.communicator.room.service.RoomResponseService;
import project.rtc.infrastructure.exception.exceptions.NoAuthorizationTokenException;
import project.rtc.infrastructure.exception.exceptions.UserNotFoundException;

@RestController
@RequiredArgsConstructor
public class RoomRestControllerImpl implements RoomRestController {
	
	private final RoomResponseService roomService;

	@Override
	public ResponseEntity<RoomResponseDto> createRoom(RoomRequestDto dto
			, HttpServletRequest httpServletRequest) throws NoAuthorizationTokenException, UserNotFoundException
			, MethodArgumentNotValidException, RoomNotFoundException {
		return new ResponseEntity<>(roomService.createRoomWithAuthoringUser(httpServletRequest, dto), HttpStatus.OK);
	}

	@Override
	public ResponseEntity<RoomResponseDto> addUserToRoom(RoomRequestDto dto
			, HttpServletRequest httpServletRequest) throws NoAuthorizationTokenException, UserNotFoundException
			, MethodArgumentNotValidException, RoomNotFoundException {
		return new ResponseEntity<>(roomService.addUserToRoom(httpServletRequest, dto), HttpStatus.OK);
	}

	@Override
	public ResponseEntity<RoomResponseDto> getRooms(RoomRequestDto dto
			, HttpServletRequest httpServletRequest) throws NoAuthorizationTokenException, UserNotFoundException
			, MethodArgumentNotValidException, RoomNotFoundException {
		return new ResponseEntity<>(roomService.getUserRooms(httpServletRequest, dto), HttpStatus.OK);
	}

	@Override
	public ResponseEntity<RoomResponseDto> removeRoom(RoomRequestDto dto
			, HttpServletRequest httpServletRequest) throws NoAuthorizationTokenException, UserNotFoundException
			, MethodArgumentNotValidException, RoomNotFoundException {
		return new ResponseEntity<>(roomService.remove(httpServletRequest, dto), HttpStatus.OK);
	}

	@Override
	public ResponseEntity<RoomResponseDto> renameRoom(RoomRequestDto dto
			, HttpServletRequest httpServletRequest) throws NoAuthorizationTokenException, UserNotFoundException
			, MethodArgumentNotValidException, RoomNotFoundException {
		return new ResponseEntity<>(roomService.renameRoom(httpServletRequest, dto), HttpStatus.OK);
	}

	@Override
	public ResponseEntity<RoomResponseDto> removeUserFromRoom(RoomRequestDto dto
			, HttpServletRequest httpServletRequest) throws NoAuthorizationTokenException, UserNotFoundException
			, MethodArgumentNotValidException, RoomNotFoundException {
		return new ResponseEntity<>(roomService.removeUserFromRoom(httpServletRequest, dto), HttpStatus.OK);
	}

	@Override
	public ResponseEntity<RoomResponseDto> leaveRoom(RoomRequestDto dto
			, HttpServletRequest httpServletRequest) throws NoAuthorizationTokenException, UserNotFoundException
			, MethodArgumentNotValidException, RoomNotFoundException {
		return new ResponseEntity<>(roomService.leaveRoom(httpServletRequest, dto), HttpStatus.OK);
	}

}
