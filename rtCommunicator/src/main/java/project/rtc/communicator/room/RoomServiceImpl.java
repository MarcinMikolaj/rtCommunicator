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

import project.rtc.communicator.room.pojo.RoomRequest;
import project.rtc.communicator.room.pojo.RoomResponse;
import project.rtc.exceptions.NoAuthorizationTokenException;
import project.rtc.exceptions.UserNotFoundException;
import project.rtc.registration.ProfilePicture;
import project.rtc.test.user.User;
import project.rtc.test.user.UserRepository;
import project.rtc.test.user.UserServiceImpl;
import project.rtc.utils.FileUtils;

@Service
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
public class RoomServiceImpl implements RoomService {
	
	private RoomRepository roomRepository;
	private UserRepository userRepository;
	private UserServiceImpl userService;

	
	public RoomServiceImpl(RoomRepository roomRepository, UserRepository userRepository, UserServiceImpl userService) {
		this.roomRepository = roomRepository;
		this.userRepository = userRepository;
		this.userService = userService;
	}
	
	@Override
	public RoomResponse createRoomWithAuthoringUser(HttpServletRequest httpServletRequest, RoomRequest roomRequest) throws ServletException {
		
		User user = null;
		RoomResponse roomResponse = new RoomResponse();
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
			create(roomRequest.getRoomName(), Collections.singletonList(user));
			roomResponse.setRooms(getRooms(user));
			roomResponse.setSuccess(true);
			roomResponse.getStatements().add(new Statement<RoomAction>(RoomAction.CREATE_ROOM, "A room has been created", StatementType.SUCCES_STATEMENT));
			return roomResponse;
		} catch (Exception e) {
			roomResponse.getStatements().add(new Statement<RoomAction>(RoomAction.CREATE_ROOM, "Create room action failed", StatementType.ERROR_STATEMENT));
			return roomResponse;
		}
	}
	
	
	
	@Override
	public RoomResponse createRoomWithFriend(HttpServletRequest httpServletRequest, RoomRequest roomRequest) {
		
		User user;
		RoomResponse roomResponse = new RoomResponse();
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
			create(roomRequest.getUserNick(), users);
			roomResponse.setSuccess(true);
			return roomResponse;

	}
	
	@Override
    public RoomResponse getUserRooms(HttpServletRequest httpServletRequest, RoomRequest roomRequest) throws ServletException{
		
		RoomResponse roomResponse = new RoomResponse();
		roomResponse.setAction(RoomAction.GET_ROOMS);
		
		try {
			List<Room> rooms = getRooms(userService.getUser(httpServletRequest));
			roomResponse.setRooms(rooms);
			roomResponse.setSuccess(true);
			return roomResponse;
		} catch (Exception e) {
			roomResponse.getStatements().add(new Statement<RoomAction>(RoomAction.GET_ROOMS, "Get rooms action failed", StatementType.ERROR_STATEMENT));
			roomResponse.setSuccess(false);
			System.out.println(e.getMessage());
			return roomResponse;
		}
	}
	
	
	@Override
	public RoomResponse remove(HttpServletRequest httpServletRequest, RoomRequest roomRequest) throws ServletException {
		
		Room room;
		RoomResponse roomResponse = new RoomResponse();
		
		roomResponse.setAction(RoomAction.REMOVE_ROOM);
		roomResponse.setSuccess(false);
		
		if(roomRequest.getRoomId() == null) {
			roomResponse.getStatements().add(new Statement<RoomAction>(RoomAction.REMOVE_ROOM, "Room not selected", StatementType.ERROR_STATEMENT));
			return roomResponse;
		}
		
		try {
	    	Optional<Room> optionalRoom = Optional.ofNullable(roomRepository.findByRoomId(roomRequest.getRoomId()));
			room = optionalRoom.get();
		} catch (Exception e) {
			e.getStackTrace();
			roomResponse.getStatements().add(new Statement<RoomAction>(RoomAction.REMOVE_ROOM, "Room not found", StatementType.ERROR_STATEMENT));
			return roomResponse;
		}
		
		if(!roomRequest.getRoomName().equals(room.getName())) {
			roomResponse.getStatements().add(new Statement<RoomAction>(RoomAction.REMOVE_ROOM, "Wrong room name", StatementType.ERROR_STATEMENT));
			return roomResponse;
		}
		
		try{
			
			List<User> users = room.getUsers();
			
			for(User u: users) {
				u.getRoomsId().remove(roomRequest.getRoomId());
				userRepository.save(u);
			}
			
			
			roomRepository.deleteById(room.getMongoId());
			
			roomResponse.setSuccess(true);
			roomResponse.getStatements().add(new Statement<RoomAction>(RoomAction.REMOVE_ROOM, "Removed !", StatementType.SUCCES_STATEMENT));
			return roomResponse;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			roomResponse.getStatements().add(new Statement<RoomAction>(RoomAction.REMOVE_ROOM, "The delete action failed", StatementType.ERROR_STATEMENT));
			return roomResponse;
		}
		
	}
	
	
	
   @Override
   public RoomResponse removeUserFromRoom(HttpServletRequest httpServletRequest, RoomRequest roomRequest) {
	   
	    Room room;
	    User sendRequestUser;
	    RoomResponse roomResponse = new RoomResponse();
	    
	    roomResponse.setAction(RoomAction.REMOVE_USER_FROM_ROOM);
	    roomResponse.setSuccess(false);
	    
	    if(roomRequest.getRoomId() == null) {
			roomResponse.getStatements().add(new Statement<RoomAction>(RoomAction.REMOVE_USER_FROM_ROOM, "Room not selected", StatementType.ERROR_STATEMENT));
			return roomResponse;
		}
	    
	    try {
	    	Optional<Room> optionalRoom = Optional.ofNullable(roomRepository.findByRoomId(roomRequest.getRoomId()));
	    	room = optionalRoom.get();
	    } catch (NoSuchElementException e) {
	    	System.out.println(e.getMessage());
			roomResponse.getStatements().add(new Statement<RoomAction>(RoomAction.REMOVE_USER_FROM_ROOM, "Room not found", StatementType.ERROR_STATEMENT));
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
	    
	    
	    if(!containUser(room, roomRequest.getUserNick())) {
	    	roomResponse.getStatements().add(new Statement<RoomAction>(RoomAction.REMOVE_USER_FROM_ROOM, "This user is not a member of the room", StatementType.ERROR_STATEMENT));
			return roomResponse;
		}
		
	    
		try {
			
			List<User> users = room.getUsers();
			
			
			for(User u: users) {	
				if(u.getNick().equals(roomRequest.getUserNick())) {
					u.getRoomsId().remove(roomRequest.getRoomId());
					userRepository.save(u);
				}
			}
					
			List<User> usersResult = users.stream()
					   .filter(u -> u != null)
					   .filter(u -> !u.getNick().equals(roomRequest.getUserNick()))
					   .collect(Collectors.toList());
			
			
			room.setUsers(usersResult);
			
			roomRepository.save(room);
			roomResponse.setRooms( getRooms(sendRequestUser));
			roomResponse.setSuccess(true);
			roomResponse.getStatements().add(new Statement<RoomAction>(RoomAction.REMOVE_USER_FROM_ROOM, "Removed !", StatementType.SUCCES_STATEMENT));
			return roomResponse;
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
			roomResponse.getStatements().add(new Statement<RoomAction>(RoomAction.REMOVE_USER_FROM_ROOM, "Failed to remove user from room",StatementType.ERROR_STATEMENT));
			return roomResponse;
		}
	}
   
	
    
	
    
	@Override
	public RoomResponse addUserToRoom(HttpServletRequest httpServletRequest, RoomRequest roomRequest) {
		
		Room room;
		User user;
		User sendRequestUser;
		RoomResponse roomResponse = new RoomResponse();
		
		roomResponse.setAction(RoomAction.ADD_USER_TO_ROOM);
		roomResponse.setSuccess(false);
		
		if(roomRequest.getRoomId() == null) {
			roomResponse.getStatements().add(new Statement<RoomAction>(RoomAction.ADD_USER_TO_ROOM, "Room not selected", StatementType.ERROR_STATEMENT));
			return roomResponse;
		}
		
		
		try {
			Optional<Room> roomOptional = Optional.ofNullable(roomRepository.findByRoomId(roomRequest.getRoomId()));
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
			room.getUsers().add(user);
			user.getRoomsId().add(room.getRoomId());
			roomRepository.save(room);
			userRepository.save(user);
			roomResponse.setRooms(getRooms(sendRequestUser));
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
	public RoomResponse renameRoom(HttpServletRequest httpServletRequest, RoomRequest roomRequest) {
		
		Room room;
		User user;
		RoomResponse roomResponse = new RoomResponse();
		
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
			Optional<Room> roomOptional = Optional.ofNullable(roomRepository.findByRoomId(roomRequest.getRoomId()));
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
			roomResponse.setRooms( getRooms(user));
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
	public RoomResponse leaveRoom(HttpServletRequest httpServletRequest, RoomRequest roomRequest) throws ServletException {
		
		Room room;
		User user;
		RoomResponse roomResponse = new RoomResponse();
		
		roomResponse.setAction(RoomAction.LEAVE_ROOM);
		roomResponse.setSuccess(false);
		
		if(roomRequest.getRoomId() == null) {
			roomResponse.getStatements().add(new Statement<RoomAction>(RoomAction.LEAVE_ROOM, "Room not selected", StatementType.ERROR_STATEMENT));
			return roomResponse;
		}
		
		try {
			Optional<Room> roomOptional = Optional.ofNullable(roomRepository.findByRoomId(roomRequest.getRoomId()));
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
			// update room	
			List <User> users = room.getUsers().stream()
			   .filter(u -> u != null)
			   .filter(u -> !u.getEmail().equals(user.getEmail()))
			   .collect(Collectors.toList());
			   
			room.setUsers(users);
			
			// update user
			List<String> roomsId = user.getRoomsId().stream()
			  .filter(r -> r != null)
			  .filter(r -> !r.equals(room.getRoomId()))
			  .collect(Collectors.toList());
	
	        user.setRoomsId(roomsId);
	
	        userRepository.save(user);
	        roomRepository.save(room);
	        
	        roomResponse.setRooms( getRooms(user));
	        roomResponse.setSuccess(true);
	        roomResponse.getStatements().add(new Statement<RoomAction>(RoomAction.LEAVE_ROOM, "Success !", StatementType.SUCCES_STATEMENT));
	        return roomResponse;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			roomResponse.getStatements().add(new Statement<RoomAction>(RoomAction.LEAVE_ROOM, "The action to leave the room failed", StatementType.ERROR_STATEMENT));
			return roomResponse;
		}		
	}
	
		
	// This method allows you to create a new room to which the users will be assigned.
	public Room create(String name, List<User> users) {
					
		Room room = new Room(createRoomId());
		room.setName(name);
				
		users.stream()
				 .filter(user -> user != null)
				 .filter(user -> user.getMongoId() != null)
				 .filter(user -> userRepository.existsById(user.getMongoId()))
				 .filter(user -> user.getRoomsId().add(room.getRoomId()))
				 .peek(user -> room.getUsers().add(user))
				 .peek(user -> userRepository.save(user))
				 .count();
					
		roomRepository.save(room);
					
		return room;
					
	}
	
		
	// This method allows you to return a list of all rooms that have been assigned to a given user
	private List<Room> getRooms(User user) {
				
		List<Room> rooms = new ArrayList<Room>();
		List<String> userRoomsID = user.getRoomsId();
				
//		userRoomsID.stream()
//			   .filter(roomId -> roomId != null)
//			   .peek(roomId -> rooms.add(roomRepository.findByRoomId(roomId)))
//			   .count();
		
		for(String roomId: userRoomsID) {
			rooms.add(roomRepository.findByRoomId(roomId));
		}
		
		for(Room r: rooms) {
			for(User u: r.getUsers()) {
				LoadUserProfileImg(u);
			}
		}
					
		return rooms;
	}
	
	
	private User LoadUserProfileImg(User user) {
		
		String pathToImg = user.getPathToProfileImg();
		
		String pictureInBase64 = FileUtils.deserializeObjectAndGetFromDirectory(pathToImg);
		ProfilePicture profilePicture = new ProfilePicture("profile.jpg", "jpg", 0, pictureInBase64);

		user.setProfilePicture(profilePicture);

		return user;
	}
	
	
	// Checks if a given room has a user
	private boolean containUser(Room room, String nick) {
		    	       
		   return room.getUsers().stream()
		    	 .filter(u -> u != null)
		    	 .anyMatch(u -> u.getNick().equals(nick));
	 }
	
	
	// This method allows you to return a unique identifier that can be used when creating a new room
	private String createRoomId() {
					
		String uniqueID;
		Room room;
					
		for(;;) {
			uniqueID = UUID.randomUUID().toString();
			room = roomRepository.findByRoomId(uniqueID);
						
			if(room == null)
				return uniqueID;
						
		}
	}
		

}
