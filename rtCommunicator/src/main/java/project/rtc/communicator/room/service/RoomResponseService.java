package project.rtc.communicator.room.service;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.MethodArgumentNotValidException;
import project.rtc.infrastructure.exception.exceptions.RoomNotFoundException;
import project.rtc.communicator.room.dto.RoomRequestDto;
import project.rtc.communicator.room.dto.RoomResponseDto;
import project.rtc.infrastructure.exception.exceptions.NoAuthorizationTokenException;
import project.rtc.infrastructure.exception.exceptions.UserNotFoundException;

public interface RoomResponseService {

	RoomResponseDto createRoomWithAuthoringUser(HttpServletRequest httpServletRequest, RoomRequestDto roomRequest)
            throws NoAuthorizationTokenException, UserNotFoundException, MethodArgumentNotValidException, RoomNotFoundException;

	RoomResponseDto getUserRooms(HttpServletRequest httpServletRequest, RoomRequestDto roomRequest)
			throws NoAuthorizationTokenException, UserNotFoundException, MethodArgumentNotValidException, RoomNotFoundException;

	RoomResponseDto remove(HttpServletRequest httpServletRequest, RoomRequestDto roomRequest)
            throws NoAuthorizationTokenException, UserNotFoundException, MethodArgumentNotValidException, RoomNotFoundException;

	RoomResponseDto renameRoom(HttpServletRequest httpServletRequest, RoomRequestDto roomRequest)
            throws NoAuthorizationTokenException, UserNotFoundException, MethodArgumentNotValidException, RoomNotFoundException;

	RoomResponseDto addUserToRoom(HttpServletRequest httpServletRequest, RoomRequestDto roomRequest)
            throws NoAuthorizationTokenException, UserNotFoundException, MethodArgumentNotValidException, RoomNotFoundException;

	RoomResponseDto removeUserFromRoom(HttpServletRequest httpServletRequest, RoomRequestDto roomRequest)
            throws NoAuthorizationTokenException, UserNotFoundException, MethodArgumentNotValidException, RoomNotFoundException;

	RoomResponseDto leaveRoom(HttpServletRequest httpServletRequest, RoomRequestDto roomRequest)
            throws NoAuthorizationTokenException, UserNotFoundException, MethodArgumentNotValidException, RoomNotFoundException;

}
