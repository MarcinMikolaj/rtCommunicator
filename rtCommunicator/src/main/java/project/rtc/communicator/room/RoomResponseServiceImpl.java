package project.rtc.communicator.room;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import project.rtc.communicator.room.pojo.RoomRequestPayload;
import project.rtc.communicator.room.pojo.RoomResponsePayload;
import project.rtc.exceptions.NoAuthorizationTokenException;
import project.rtc.exceptions.UserNotFoundException;
import project.rtc.registration.ProfilePicture;
import project.rtc.test.user.User;
import project.rtc.test.user.UserRepository;
import project.rtc.test.user.UserServiceImpl;
import project.rtc.utils.FileUtils;

@Service
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
public class RoomResponseServiceImpl implements RoomResponseService {
	
	private RoomRepository roomRepository;
	private UserRepository userRepository;
	private UserServiceImpl userService;
	
	private RoomService roomService;

	
	public RoomResponseServiceImpl(RoomRepository roomRepository, UserRepository userRepository, UserServiceImpl userServiceImpl, RoomServiceImpl roomServiceImpl) {
		this.roomRepository = roomRepository;
		this.userRepository = userRepository;
		this.userService = userServiceImpl;
		this.roomService = roomServiceImpl;
	}
	
	@Override
	public RoomResponsePayload createRoomWithAuthoringUser(HttpServletRequest httpServletRequest, RoomRequestPayload roomRequest) throws ServletException {
		
		User user = null;
		RoomResponsePayload roomResponse = new RoomResponsePayload();
		roomResponse.setSuccess(false);
		
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
			roomResponse.getStatements().add(new Statement<RoomAction>(RoomAction.CREATE_ROOM, "Create room action failed", StatementType.ERROR_STATEMENT));
			return roomResponse;
		}
	}
	
	
	
	@Override
	public RoomResponsePayload createRoomWithFriend(HttpServletRequest httpServletRequest, RoomRequestPayload roomRequest) {
		
		User user;
		RoomResponsePayload roomResponse = new RoomResponsePayload();
		List<User> users = new ArrayList<User>();
		
		roomResponse.setAction(RoomAction.CREATE_ROOM);
		roomResponse.setSuccess(false);
		
		
		try {
			user = userService.getUser(httpServletRequest);
		} catch (UserNotFoundException e) {
			System.out.print(e.getMessage());
			roomResponse.getStatements().add(new Statement<RoomAction>(RoomAction.CREATE_ROOM, "Create room action failed", StatementType.ERROR_STATEMENT));
			return roomResponse;
		} catch (NoAuthorizationTokenException e) {
			System.out.print(e.getMessage());
			roomResponse.getStatements().add(new Statement<RoomAction>(RoomAction.CREATE_ROOM, "No permission", StatementType.ERROR_STATEMENT));
			return roomResponse;
		} 
		
			users.add(user);
			users.add(userRepository.findByNick(roomRequest.getUserNick()));  
			roomService.createRoom(roomRequest.getUserNick(), users);
			roomResponse.setSuccess(true);
			return roomResponse;

	}
	
	@Override
    public RoomResponsePayload getUserRooms(HttpServletRequest httpServletRequest, RoomRequestPayload roomRequest) throws ServletException{
		
		RoomResponsePayload roomResponse = new RoomResponsePayload();
		roomResponse.setAction(RoomAction.GET_ROOMS);
		
		try {
			roomResponse.setRooms(roomService.getUserRooms(userService.getUser(httpServletRequest)));
			roomResponse.setSuccess(true);
			return roomResponse;
		} catch (Exception e) {
			roomResponse.getStatements().add(new Statement<RoomAction>(RoomAction.GET_ROOMS, "Get rooms action failed", StatementType.ERROR_STATEMENT));
			roomResponse.setSuccess(false);
			System.out.print(e.getStackTrace());
			return roomResponse;
		}
	}
	
	
	@Override
	public RoomResponsePayload remove(HttpServletRequest httpServletRequest, RoomRequestPayload roomRequest) throws ServletException {
		
		Room room;
		User user;
		RoomResponsePayload roomResponse = new RoomResponsePayload();
		
		roomResponse.setAction(RoomAction.REMOVE_ROOM);
		roomResponse.setSuccess(false);
		
		// Call if the user has not selected a room
		if(roomRequest.getRoomId() == null) {
			roomResponse.getStatements().add(new Statement<RoomAction>(RoomAction.REMOVE_ROOM, "Room not selected", StatementType.ERROR_STATEMENT));
			return roomResponse;
		}
		
		// Try to get user
		try {
			user = userService.getUser(httpServletRequest);
		} catch (UserNotFoundException | NoAuthorizationTokenException e) {
			e.getStackTrace();
			roomResponse.getStatements().add(new Statement<RoomAction>(RoomAction.REMOVE_ROOM, "No user found", StatementType.ERROR_STATEMENT));
			return roomResponse;
		}
		
		// Try to download the room the user is asking for
		try {
			room = roomService.getRoom(roomRequest.getRoomId());
		} catch (NoSuchElementException e) {
			e.getStackTrace();
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
			e.getStackTrace();
			roomResponse.getStatements().add(new Statement<RoomAction>(RoomAction.REMOVE_ROOM, "The delete action failed", StatementType.ERROR_STATEMENT));
			return roomResponse;
		}
		
		return roomResponse;
	}
	
	
	
   @Override
   public RoomResponsePayload removeUserFromRoom(HttpServletRequest httpServletRequest, RoomRequestPayload roomRequest) {
	   
	    Room room;
	    User sendRequestUser;
	    RoomResponsePayload roomResponse = new RoomResponsePayload();
	    
	    roomResponse.setAction(RoomAction.REMOVE_USER_FROM_ROOM);
	    roomResponse.setSuccess(false);
	    
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
			e.getStackTrace();
			return roomResponse;
		} catch (NoAuthorizationTokenException e) {
			roomResponse.getStatements().add(new Statement<RoomAction>(RoomAction.ADD_USER_TO_ROOM, "No permission", StatementType.ERROR_STATEMENT));
			e.getStackTrace();
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
			e.getStackTrace();
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
		RoomResponsePayload roomResponse = new RoomResponsePayload();
		
		roomResponse.setAction(RoomAction.ADD_USER_TO_ROOM);
		roomResponse.setSuccess(false);
		
		if(roomRequest.getRoomId() == null) {
			roomResponse.getStatements().add(new Statement<RoomAction>(RoomAction.ADD_USER_TO_ROOM, "Room not selected", StatementType.ERROR_STATEMENT));
			return roomResponse;
		}
		
		
		try {
			Optional<Room> roomOptional = roomRepository.findByRoomId(roomRequest.getRoomId());
			room = roomOptional.get();
		} catch (NoSuchElementException e) {
			roomResponse.getStatements().add(new Statement<RoomAction>(RoomAction.ADD_USER_TO_ROOM, "User not found", StatementType.ERROR_STATEMENT));
			System.out.println(e.getMessage());
			return roomResponse;
		}
		
		try {
			Optional<User> userOptional = Optional.ofNullable(userRepository.findByNick(roomRequest.getUserNick()));
			user = userOptional.get();
		} catch (NoSuchElementException e) {
			roomResponse.getStatements().add(new Statement<RoomAction>(RoomAction.ADD_USER_TO_ROOM, "User not found", StatementType.ERROR_STATEMENT));
			System.out.println(e.getMessage());
			return roomResponse;
		}
		
		
		
		try {
			sendRequestUser = userService.getUser(httpServletRequest);
		} catch (UserNotFoundException e) {
			roomResponse.getStatements().add(new Statement<RoomAction>(RoomAction.ADD_USER_TO_ROOM, "User not found", StatementType.ERROR_STATEMENT));
			System.out.println(e.getMessage());
			return roomResponse;
		} catch (NoAuthorizationTokenException e) {
			roomResponse.getStatements().add(new Statement<RoomAction>(RoomAction.ADD_USER_TO_ROOM, "No permission", StatementType.ERROR_STATEMENT));
			System.out.println(e.getMessage());
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
			System.out.println(e.getMessage());
			return roomResponse;
		}
	}
	
	
    // This method allows you to change the name of the room
	@Override
	public RoomResponsePayload renameRoom(HttpServletRequest httpServletRequest, RoomRequestPayload roomRequest) {
		
		Room room;
		User user;
		RoomResponsePayload roomResponse = new RoomResponsePayload();
		
		roomResponse.setAction(RoomAction.RENAME_ROOM);
		roomResponse.setSuccess(false);
		
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
			Optional<Room> roomOptional = roomRepository.findByRoomId(roomRequest.getRoomId());
			room = roomOptional.get();
		} catch (NoSuchElementException e) {
			System.out.println("RoomServiceImpl.renameRoom: " + e.getMessage());
			roomResponse.getStatements().add(new Statement<RoomAction>(RoomAction.RENAME_ROOM, "Your room was not found", StatementType.ERROR_STATEMENT));
			return roomResponse;
		}
		
		
		// try to get the user
		try {
			Optional<User> userOptional = Optional.ofNullable(userService.getUser(httpServletRequest));
			user = userOptional.get();
			
		} catch (Exception e) {
			System.out.println("RoomServiceImpl.renameRoom: " + e.getMessage());
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
			System.out.println("RoomServiceImpl.renameRoom: " + e.getMessage());
			roomResponse.getStatements().add(new Statement<RoomAction>(RoomAction.RENAME_ROOM, "The rename action failed", StatementType.ERROR_STATEMENT));
			return roomResponse;
		}
	}

	

	@Override
	public RoomResponsePayload leaveRoom(HttpServletRequest httpServletRequest, RoomRequestPayload roomRequest) throws ServletException {
		
		Room room;
		User user;
		RoomResponsePayload roomResponse = new RoomResponsePayload();
		
		roomResponse.setAction(RoomAction.LEAVE_ROOM);
		roomResponse.setSuccess(false);
		
		if(roomRequest.getRoomId() == null) {
			roomResponse.getStatements().add(new Statement<RoomAction>(RoomAction.LEAVE_ROOM, "Room not selected", StatementType.ERROR_STATEMENT));
			return roomResponse;
		}
		
		try {
			Optional<Room> roomOptional = roomRepository.findByRoomId(roomRequest.getRoomId());
			room = roomOptional.get();
		} catch (Exception e) {
			System.out.println(e.getMessage());
			roomResponse.getStatements().add(new Statement<RoomAction>(RoomAction.LEAVE_ROOM, "Room not found", StatementType.ERROR_STATEMENT));
			return roomResponse;
		}
		
		try {
			Optional<User> userOptional = Optional.ofNullable(userService.getUser(httpServletRequest));
			user = userOptional.get();
		} catch (Exception e) {
			System.out.println(e.getMessage());
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
