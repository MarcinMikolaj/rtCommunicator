package project.rtc.communicator.room.service.impl;

import java.util.*;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import org.springframework.web.bind.MethodArgumentNotValidException;
import project.rtc.communicator.invitations.services.InvitationService;
import project.rtc.communicator.messager.entities.Message;
import project.rtc.communicator.room.dto.*;
import project.rtc.communicator.room.entities.Room;
import project.rtc.communicator.user.services.UserService;
import project.rtc.infrastructure.exception.exceptions.MessageNotFoundException;
import project.rtc.infrastructure.exception.exceptions.RoomNotFoundException;
import project.rtc.communicator.messager.services.MessageService;
import project.rtc.communicator.room.service.RoomService;
import project.rtc.communicator.room.service.RoomResponseService;
import project.rtc.communicator.user.entities.User;
import project.rtc.infrastructure.exception.exceptions.NoAuthorizationTokenException;
import project.rtc.infrastructure.exception.exceptions.UserNotFoundException;

@Service
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
@RequiredArgsConstructor
public class RoomResponseServiceImpl implements RoomResponseService {

	private final UserService userService;
	private final MessageService messageService;
	private final RoomService roomService;
	private final InvitationService invitationService;

	@Override
	public RoomResponseDto createRoomWithAuthoringUser(HttpServletRequest httpServletRequest, RoomRequestDto dto)
			throws UserNotFoundException, RoomNotFoundException {
		roomService.create(dto.getRoomName(), dto.getUserId());
		return prepareRoomResponsePayload(HttpStatus.OK, RoomOperation.CREATE_ROOM, prepareRoomDtoList(dto.getUserId())
				, getUnreadMessageForRooms(dto.getUserId()));
	}

	@Override
    public RoomResponseDto getUserRooms(HttpServletRequest httpServletRequest, RoomRequestDto dto)
			throws UserNotFoundException, RoomNotFoundException, MessageNotFoundException {
		if(dto.getRoomId() != null && dto.getRoomId().equals("none") == false)
			messageService.updateAllMessageForRoomAsReadBy(dto.getRoomId(), dto.getUserId());
		return prepareRoomResponsePayload(HttpStatus.OK, RoomOperation.GET_ROOMS
				, prepareRoomDtoList(dto.getUserId()), getUnreadMessageForRooms(dto.getUserId()));
	}

	@Override
	public RoomResponseDto remove(HttpServletRequest httpServletRequest, RoomRequestDto dto)
			throws NoAuthorizationTokenException, UserNotFoundException, MethodArgumentNotValidException, RoomNotFoundException {
		roomService.removeRoom(dto.getRoomId());
		return prepareRoomResponsePayload(HttpStatus.OK, RoomOperation.REMOVE_ROOM
				, prepareRoomDtoList(dto.getUserId()), getUnreadMessageForRooms(dto.getUserId()));
	}

   @Override
   public RoomResponseDto removeUserFromRoom(HttpServletRequest httpServletRequest, RoomRequestDto dto)
		   throws UserNotFoundException, RoomNotFoundException {
		roomService.removeUserFromRoom(dto.getRoomId(), dto.getChangedUserId());
	    return prepareRoomResponsePayload(HttpStatus.OK, RoomOperation.REMOVE_USER_FROM_ROOM
				, prepareRoomDtoList(dto.getUserId()), getUnreadMessageForRooms(dto.getUserId()));
	}

	@Override
	public RoomResponseDto addUserToRoom(HttpServletRequest httpServletRequest, RoomRequestDto dto)
			throws UserNotFoundException, RoomNotFoundException, NoAuthorizationTokenException {
		User executingUser = userService.getUser(httpServletRequest);
		invitationService.create(dto.getRoomId(), dto.getUserNick(), executingUser.getNick());
		return prepareRoomResponsePayload(HttpStatus.OK, RoomOperation.ADD_USER_TO_ROOM, prepareRoomDtoList(dto.getUserId())
				, getUnreadMessageForRooms(dto.getUserId()));
	}

	@Override
	public RoomResponseDto renameRoom(HttpServletRequest httpServletRequest, RoomRequestDto dto)
			throws UserNotFoundException, RoomNotFoundException {
		roomService.updateRoomName(dto.getRoomId(), dto.getRoomName());
		return prepareRoomResponsePayload(HttpStatus.OK, RoomOperation.RENAME_ROOM, prepareRoomDtoList(dto.getUserId())
				, getUnreadMessageForRooms(dto.getUserId()));
	}

	@Override
	public RoomResponseDto leaveRoom(HttpServletRequest httpServletRequest, RoomRequestDto dto)
			throws UserNotFoundException, RoomNotFoundException {
		roomService.removeUserFromRoom(dto.getRoomId(), dto.getUserId());
		return prepareRoomResponsePayload(HttpStatus.OK, RoomOperation.LEAVE_ROOM, prepareRoomDtoList(dto.getUserId())
				, getUnreadMessageForRooms(dto.getUserId()));
	}

	private Map<String, Integer> getUnreadMessageForRooms(String userId) throws UserNotFoundException, RoomNotFoundException {
		List<Room> rooms = roomService.getAllUserRooms(userId);
		User user = userService.getUser(userId);
		return countUnreadMessages(rooms, user.getUserId());
	}
	
	private Map<String, Integer> countUnreadMessages(List<Room> roomList, String userId) {
		Map<String, Integer> unreadMessages = new HashMap<>();
		roomList.stream()
		   .filter(Objects::nonNull)
		   .filter(r -> r.getRoomId() != null)
		   .peek(r -> unreadMessages.put(r.getRoomId(), count(r, userId)))
		   .collect(Collectors.toList()).size();
		return unreadMessages;
	}

	private int count(Room room, String userId) {
		return messageService.getAllMessageFromRoom(room.getRoomId()).stream()
		   .filter(Objects::nonNull)
		   .filter(m -> m.getMissedBy().contains(userId))
		   .collect(Collectors.toList())
		   .size();
	}

	private RoomResponseDto prepareRoomResponsePayload(HttpStatus httpStatus, RoomOperation operation, List<RoomDto> roomsDto
			, Map<String, Integer> unread){
		return RoomResponseDto.builder()
				.timestamp(new Date())
				.status(httpStatus.value())
				.operation(operation)
				.rooms(roomsDto)
				.unreadMessages(unread)
				.build();
	}

	private List<RoomDto> prepareRoomDtoList(String userId) throws UserNotFoundException, RoomNotFoundException {
		List<RoomDto> roomsDto = new ArrayList<>();
		List<Room> rooms = roomService.getAllUserRooms(userId);

		for(Room r:rooms){
			roomsDto.add(buildRoomD(r
					, roomService.getUsersFromRoom(r.getRoomId(), true)
					, messageService.getAllMessageFromRoom(r.getRoomId())));
		}
		return roomsDto;
	}

	private RoomDto buildRoomD(Room room, List<User> users, List<Message> messages){
		return new RoomDto(room, users, messages);
	}

}
