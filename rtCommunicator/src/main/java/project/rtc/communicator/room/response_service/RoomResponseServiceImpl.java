package project.rtc.communicator.room.response_service;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import project.rtc.communicator.messager.Message;
import project.rtc.communicator.messager.MessageService;
import project.rtc.communicator.messager.MessageServiceImpl;
import project.rtc.communicator.room.Room;
import project.rtc.communicator.room.RoomAction;
import project.rtc.communicator.room.RoomRepository;
import project.rtc.communicator.room.RoomService;
import project.rtc.communicator.room.RoomServiceImpl;
import project.rtc.communicator.room.pojo.RoomRequestPayload;
import project.rtc.communicator.room.pojo.RoomResponsePayload;
import project.rtc.communicator.room.pojo.Statement;
import project.rtc.communicator.room.pojo.StatementType;
import project.rtc.communicator.user.User;
import project.rtc.communicator.user.UserRepository;
import project.rtc.communicator.user.UserServiceImpl;
import project.rtc.exceptions.NoAuthorizationTokenException;
import project.rtc.exceptions.UserNotFoundException;

@Service
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
public class RoomResponseServiceImpl implements RoomResponseService {
	
	private RoomRepository roomRepository;
	private UserRepository userRepository;
	private UserServiceImpl userService;
	private MessageService messageService;
	
	private RoomService roomService;

	
	public RoomResponseServiceImpl(RoomRepository roomRepository, UserRepository userRepository,
			UserServiceImpl userServiceImpl, RoomServiceImpl roomServiceImpl, MessageServiceImpl messageServiceImpl) {
		this.roomRepository = roomRepository;
		this.userRepository = userRepository;
		this.userService = userServiceImpl;
		this.roomService = roomServiceImpl;
		this.messageService = messageServiceImpl;
	}
	
	@Override
	public RoomResponsePayload createRoomWithAuthoringUser(HttpServletRequest httpServletRequest, RoomRequestPayload roomRequest) throws ServletException {
		
		User user = null;
		RoomResponsePayload roomResponse = new RoomResponsePayload(RoomAction.CREATE_ROOM);
	
		if(roomRequest.getRoomName() == null  || roomRequest.getRoomName().equals("")) {
			roomResponse.getStatements().add(new Statement<RoomAction>(RoomAction.CREATE_ROOM, "The room name cannot be empty", StatementType.ERROR_STATEMENT));
			return roomResponse;
		}
		
		
		try {
			user = userService.getUser(httpServletRequest);
		} catch (UserNotFoundException e) {
			e.printStackTrace();
			roomResponse.getStatements().add(new Statement<RoomAction>(RoomAction.CREATE_ROOM, "User not found", StatementType.ERROR_STATEMENT));
			return roomResponse;
		} catch (NoAuthorizationTokenException e) {
			e.printStackTrace();
			roomResponse.getStatements().add(new Statement<RoomAction>(RoomAction.CREATE_ROOM, "No permission", StatementType.ERROR_STATEMENT));
			return roomResponse;
		}
		
		
		try {
			roomService.createRoom(roomRequest.getRoomName(), Collections.singletonList(user));
			roomResponse.setRooms(roomService.getUserRooms(user));
			roomResponse.setSuccess(true);
			roomResponse.getStatements().add(new Statement<RoomAction>(RoomAction.CREATE_ROOM, "A room has been created", StatementType.SUCCES_STATEMENT));
			return roomResponse;
		} catch (Exception e) {
			e.printStackTrace();
			roomResponse.getStatements().add(new Statement<RoomAction>(RoomAction.CREATE_ROOM, "Create room action failed", StatementType.ERROR_STATEMENT));
			return roomResponse;
		}
	}
	
	
	
//	@Override
//	public RoomResponsePayload createRoomWithFriend(HttpServletRequest httpServletRequest, RoomRequestPayload roomRequest) {
//		
//		User user;
//		User friend;
//		RoomResponsePayload roomResponse = new RoomResponsePayload(RoomAction.CREATE_ROOM_WITH_FRIEND);
//		
//		List<User> users = new ArrayList<User>();
//		
//		
//		try {
//			user = userService.getUser(httpServletRequest);
//			users.add(user);
//		} catch (UserNotFoundException | NoAuthorizationTokenException e) {
//			e.printStackTrace();
//			roomResponse.getStatements().add(new Statement<RoomAction>(RoomAction.CREATE_ROOM_WITH_FRIEND, "Create room action failed", StatementType.ERROR_STATEMENT));
//			return roomResponse;
//		}
//		
//		try {
//			friend = userRepository.findByNick(roomRequest.getUserNick()).get();
//			users.add(friend);
//		} catch (NoSuchElementException e) {
//			System.out.println("RoomResponseServiceimpl.createRoomWithFriend: User nof found");
//			roomResponse.getStatements().add(new Statement<RoomAction>(RoomAction.CREATE_ROOM_WITH_FRIEND, "User not found", StatementType.ERROR_STATEMENT));
//			return roomResponse;
//		}
//		
//			
//			roomService.createRoom(roomRequest.getUserNick(), users);
//			
//			roomResponse.getStatements().add(new Statement<RoomAction>(RoomAction.CREATE_ROOM_WITH_FRIEND, "Room has been successfully created", StatementType.SUCCES_STATEMENT));
//			roomResponse.setSuccess(true);
//			
//			return roomResponse;
//
//	}
	
	@Override
    public RoomResponsePayload getUserRooms(HttpServletRequest httpServletRequest, RoomRequestPayload roomRequest) throws ServletException{
		
		User user;
		List<Room> rooms;
		
		RoomResponsePayload roomResponse = new RoomResponsePayload(RoomAction.GET_ROOMS);
		
		try {
			user = userService.getUser(httpServletRequest);		
			rooms = roomService.getUserRooms(user);
			
			System.out.println("FFFFFFFFFFFFFF: " + roomRequest.getRoomId());
			if(roomRequest.getRoomId() != null ) {
				messageService.addReadBy(roomRequest.getRoomId(), roomRequest.getUserNick());
				rooms = roomService.getUserRooms(user);
			}
			
			
			Map<String, Integer> ss = countUnreadMessages(rooms, user.getNick());
			
			for (Map.Entry<String, Integer> entry : ss.entrySet()) {
		        System.out.println(entry.getKey() + ":" + entry.getValue());
		    }
			
			roomResponse.setUnreadMessages(ss);
			roomResponse.setRooms(rooms);
			
			
			roomResponse.getStatements().add(new Statement<RoomAction>(RoomAction.GET_ROOMS, "Get rooms action success !", StatementType.SUCCES_STATEMENT));
			roomResponse.setSuccess(true);
			return roomResponse;
		} catch (Exception e) {
			roomResponse.getStatements().add(new Statement<RoomAction>(RoomAction.GET_ROOMS, "Get rooms action failed", StatementType.ERROR_STATEMENT));
			roomResponse.setSuccess(false);
			e.printStackTrace();
			return roomResponse;
		}
	}
	
	private Map<String, Integer> countUnreadMessages(List<Room> roomList, String userNick) {
		
		Map<String, Integer> unreadMessages = new HashMap<String, Integer>();
		
		roomList.stream()
		   .filter(r -> r != null)
		   .filter(r -> r.getRoomId() != null)
		   .peek(r -> unreadMessages.put(r.getRoomId(), count(r, userNick)))
		   .collect(Collectors.toList()).size();
		  
		return unreadMessages;
	}
	
	
	private int count(Room room, String userNick) {
		
		return room.getMessages().stream()
		   .filter(m -> m != null)
		   .filter(m -> m.getMissedBy().contains(userNick))
		   .collect(Collectors.toList())
		   .size();
	}
	
	
	@Override
	public RoomResponsePayload remove(HttpServletRequest httpServletRequest, RoomRequestPayload roomRequest) throws ServletException {
		
		Room room;
		User user;
		RoomResponsePayload roomResponse = new RoomResponsePayload(RoomAction.REMOVE_ROOM);
		
		
		// Call if the user has not selected a room
		if(roomRequest.getRoomId() == null) {
			roomResponse.getStatements().add(new Statement<RoomAction>(RoomAction.REMOVE_ROOM, "Room not selected", StatementType.ERROR_STATEMENT));
			return roomResponse;
		}
		
		// Try to get user
		try {
			user = userService.getUser(httpServletRequest);
		} catch (UserNotFoundException | NoAuthorizationTokenException e) {
			e.printStackTrace();
			roomResponse.getStatements().add(new Statement<RoomAction>(RoomAction.REMOVE_ROOM, "No user found", StatementType.ERROR_STATEMENT));
			return roomResponse;
		}
		
		// Try to download the room the user is asking for
		try {
			room = roomService.getRoom(roomRequest.getRoomId());
		} catch (NoSuchElementException e) {
			e.printStackTrace();
			roomResponse.getStatements().add(new Statement<RoomAction>(RoomAction.REMOVE_ROOM, "No room found", StatementType.ERROR_STATEMENT));
			return roomResponse;
		}
		
		if(!room.getName().equals(roomRequest.getRoomName())) {
			roomResponse.getStatements().add(new Statement<RoomAction>(RoomAction.REMOVE_ROOM, "Invalid room name.", StatementType.ERROR_STATEMENT));
			return roomResponse;
		}
		
		// Try to delete the room
		try {
			roomService.deleteRoom(room);
			roomResponse.setRooms(roomService.getUserRooms(user));
			roomResponse.setSuccess(true);
			roomResponse.getStatements().add(new Statement<RoomAction>(RoomAction.REMOVE_ROOM, "Removed !", StatementType.SUCCES_STATEMENT));
		} catch (NullPointerException | IllegalArgumentException | UserNotFoundException e) {
			e.printStackTrace();
			roomResponse.getStatements().add(new Statement<RoomAction>(RoomAction.REMOVE_ROOM, "The delete action failed", StatementType.ERROR_STATEMENT));
			return roomResponse;
		}
		
		return roomResponse;
	}
	
	
	
   @Override
   public RoomResponsePayload removeUserFromRoom(HttpServletRequest httpServletRequest, RoomRequestPayload roomRequest) {
	   
	    Room room;
	    User sendRequestUser;
	    RoomResponsePayload roomResponse = new RoomResponsePayload(RoomAction.REMOVE_USER_FROM_ROOM);
	    
	   
	    if(roomRequest.getRoomId() == null) {
			roomResponse.getStatements().add(new Statement<RoomAction>(RoomAction.REMOVE_USER_FROM_ROOM, "Room not selected", StatementType.ERROR_STATEMENT));
			return roomResponse;
		}
	    
	    try {
	    	room = roomRepository.findByRoomId(roomRequest.getRoomId()).get();
	    } catch (NoSuchElementException e) {
	    	System.out.println(e.getMessage());
			roomResponse.getStatements().add(new Statement<RoomAction>(RoomAction.REMOVE_USER_FROM_ROOM, "Room not found", StatementType.ERROR_STATEMENT));
			return roomResponse;
	    }
	    
	    // Try to get the user executing the query
	    try {
	    	sendRequestUser = userService.getUser(httpServletRequest);
		} catch (UserNotFoundException e) {
			roomResponse.getStatements().add(new Statement<RoomAction>(RoomAction.ADD_USER_TO_ROOM, "User not found", StatementType.ERROR_STATEMENT));
			e.printStackTrace();
			return roomResponse;
		} catch (NoAuthorizationTokenException e) {
			roomResponse.getStatements().add(new Statement<RoomAction>(RoomAction.ADD_USER_TO_ROOM, "No permission", StatementType.ERROR_STATEMENT));
			e.printStackTrace();
			return roomResponse;
		}
	    
	    
	    if(!containUser(room, roomRequest.getUserNick())) {
	    	roomResponse.getStatements().add(new Statement<RoomAction>(RoomAction.REMOVE_USER_FROM_ROOM, "This user is not a member of the room", StatementType.ERROR_STATEMENT));
			return roomResponse;
		}
		
	    
		try {
			roomService.deleteUserFromRoom(room, roomRequest.getUserNick());			
			roomResponse.setRooms(roomService.getUserRooms(sendRequestUser));
			roomResponse.setSuccess(true);
			roomResponse.getStatements().add(new Statement<RoomAction>(RoomAction.REMOVE_USER_FROM_ROOM, "Removed !", StatementType.SUCCES_STATEMENT));
			return roomResponse;
			
		} catch (NullPointerException | IllegalArgumentException e) {
			e.printStackTrace();
			roomResponse.getStatements().add(new Statement<RoomAction>(RoomAction.REMOVE_USER_FROM_ROOM, "User don't exist",StatementType.ERROR_STATEMENT));
			return roomResponse;
		} catch (UserNotFoundException e) {
			e.printStackTrace();
			roomResponse.getStatements().add(new Statement<RoomAction>(RoomAction.REMOVE_USER_FROM_ROOM, "Failed to remove user from room",StatementType.ERROR_STATEMENT));
			return roomResponse;
		}
		
		
	}
	
   
	
	@Override
	public RoomResponsePayload addUserToRoom(HttpServletRequest httpServletRequest, RoomRequestPayload roomRequest) {
		
		Room room;
		User user;
		User sendRequestUser;
		RoomResponsePayload roomResponse = new RoomResponsePayload(RoomAction.ADD_USER_TO_ROOM);
		
		
		if(roomRequest.getRoomId() == null) {
			roomResponse.getStatements().add(new Statement<RoomAction>(RoomAction.ADD_USER_TO_ROOM, "Room not selected", StatementType.ERROR_STATEMENT));
			return roomResponse;
		}
		
		
		try {
			room = roomRepository.findByRoomId(roomRequest.getRoomId()).get();
		} catch (NoSuchElementException e) {
			roomResponse.getStatements().add(new Statement<RoomAction>(RoomAction.ADD_USER_TO_ROOM, "User not found", StatementType.ERROR_STATEMENT));
			e.printStackTrace();
			return roomResponse;
		}
		
		try {
			user = userRepository.findByNick(roomRequest.getUserNick()).get();
		} catch (NoSuchElementException e) {
			roomResponse.getStatements().add(new Statement<RoomAction>(RoomAction.ADD_USER_TO_ROOM, "User not found", StatementType.ERROR_STATEMENT));
			e.printStackTrace();
			return roomResponse;
		}
		
		
		
		try {
			sendRequestUser = userService.getUser(httpServletRequest);
		} catch (UserNotFoundException e) {
			roomResponse.getStatements().add(new Statement<RoomAction>(RoomAction.ADD_USER_TO_ROOM, "User not found", StatementType.ERROR_STATEMENT));
			e.printStackTrace();
			return roomResponse;
		} catch (NoAuthorizationTokenException e) {
			roomResponse.getStatements().add(new Statement<RoomAction>(RoomAction.ADD_USER_TO_ROOM, "No permission", StatementType.ERROR_STATEMENT));
			e.printStackTrace();
			return roomResponse;
		}
		
		
		if(containUser(room, roomRequest.getUserNick())) {
			roomResponse.getStatements().add(new Statement<RoomAction>(RoomAction.ADD_USER_TO_ROOM, "This user is already a member of the room", StatementType.ERROR_STATEMENT));
			return roomResponse;
		}
		
		
		try {
			roomService.addUserToRoom(room, user);
			
			roomResponse.setRooms(roomService.getUserRooms(sendRequestUser));
			roomResponse.setSuccess(true);
			roomResponse.getStatements().add(new Statement<RoomAction>(RoomAction.ADD_USER_TO_ROOM, "User added !", StatementType.SUCCES_STATEMENT));
			return roomResponse;
		} catch (Exception e) {
			roomResponse.getStatements().add(new Statement<RoomAction>(RoomAction.ADD_USER_TO_ROOM, "Failed to add user to room", StatementType.ERROR_STATEMENT));
			e.printStackTrace();
			return roomResponse;
		}
	}
	
	
    // This method allows you to change the name of the room
	@Override
	public RoomResponsePayload renameRoom(HttpServletRequest httpServletRequest, RoomRequestPayload roomRequest) {
		
		Room room;
		User user;
		RoomResponsePayload roomResponse = new RoomResponsePayload(RoomAction.RENAME_ROOM);
		
		
		if(roomRequest.getRoomId() == null) {
			roomResponse.getStatements().add(new Statement<RoomAction>(RoomAction.RENAME_ROOM, "Room not selected", StatementType.ERROR_STATEMENT));
			return roomResponse;
		}
		
		if(roomRequest.getRoomName() == null || roomRequest.getRoomName().equals("")) {
			roomResponse.getStatements().add(new Statement<RoomAction>(RoomAction.RENAME_ROOM, "The name of the room cannot be empty", StatementType.ERROR_STATEMENT));
			return roomResponse;
		}
		
		// try to get the room
		try {
			room = roomRepository.findByRoomId(roomRequest.getRoomId()).get();
		} catch (NoSuchElementException e) {
			e.printStackTrace();
			roomResponse.getStatements().add(new Statement<RoomAction>(RoomAction.RENAME_ROOM, "Your room was not found", StatementType.ERROR_STATEMENT));
			return roomResponse;
		}
		
		
		// try to get the user
		try {
			user = userService.getUser(httpServletRequest);
		} catch (Exception e) {
			e.printStackTrace();
			roomResponse.getStatements().add(new Statement<RoomAction>(RoomAction.RENAME_ROOM, "User not found", StatementType.ERROR_STATEMENT));
			return roomResponse;
		}
		
		// try to save the changes
		room.setName(roomRequest.getRoomName());
		
		// try save 
		try {
			roomRepository.save(room);
			roomResponse.setSuccess(true);
			roomResponse.setRooms(roomService.getUserRooms(user));
			roomResponse.setSuccess(true);
			roomResponse.getStatements().add(new Statement<RoomAction>(RoomAction.RENAME_ROOM, "Success !", StatementType.SUCCES_STATEMENT));
            return roomResponse;
		} catch (Exception e) {
			e.printStackTrace();
			roomResponse.getStatements().add(new Statement<RoomAction>(RoomAction.RENAME_ROOM, "The rename action failed", StatementType.ERROR_STATEMENT));
			return roomResponse;
		}
	}

	

	@Override
	public RoomResponsePayload leaveRoom(HttpServletRequest httpServletRequest, RoomRequestPayload roomRequest) throws ServletException {
		
		Room room;
		User user;
		RoomResponsePayload roomResponse = new RoomResponsePayload(RoomAction.LEAVE_ROOM);
	
		
		if(roomRequest.getRoomId() == null) {
			roomResponse.getStatements().add(new Statement<RoomAction>(RoomAction.LEAVE_ROOM, "Room not selected", StatementType.ERROR_STATEMENT));
			return roomResponse;
		}
		
		try {
			Optional<Room> roomOptional = roomRepository.findByRoomId(roomRequest.getRoomId());
			room = roomOptional.get();
		} catch (Exception e) {
			e.printStackTrace();
			roomResponse.getStatements().add(new Statement<RoomAction>(RoomAction.LEAVE_ROOM, "Room not found", StatementType.ERROR_STATEMENT));
			return roomResponse;
		}
		
		try {
			user = userService.getUser(httpServletRequest);
		} catch (Exception e) {
			e.printStackTrace();
			roomResponse.getStatements().add(new Statement<RoomAction>(RoomAction.LEAVE_ROOM, "User not found", StatementType.ERROR_STATEMENT));
			return roomResponse;
		}
		
		
		try {
			roomService.deleteUserFromRoom(room, user.getNick());
	        roomResponse.setRooms(roomService.getUserRooms(user));
	        roomResponse.setSuccess(true);
	        roomResponse.getStatements().add(new Statement<RoomAction>(RoomAction.LEAVE_ROOM, "Success !", StatementType.SUCCES_STATEMENT));
	        return roomResponse;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			roomResponse.getStatements().add(new Statement<RoomAction>(RoomAction.LEAVE_ROOM, "The action to leave the room failed", StatementType.ERROR_STATEMENT));
			return roomResponse;
		}		
	}
	
		

	// Checks if a given room has a user
	private boolean containUser(Room room, String nick) {
		    	       
		   return room.getUsers().stream()
		    	 .filter(u -> u != null)
		    	 .anyMatch(u -> u.getNick().equals(nick));
	 }
}
