package project.rtc.communicator.room.response_service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import project.rtc.communicator.room.repositories.RoomRepository;
import project.rtc.communicator.room.dto.Room;
import project.rtc.communicator.room.response_service.RoomService;
import project.rtc.communicator.user.dto.User;
import project.rtc.communicator.user.repositories.UserRepository;
import project.rtc.exceptions.UserNotFoundException;
import project.rtc.registration.dto.ProfilePicture;
import project.rtc.utils.FileUtils;

@Service
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
public class RoomServiceImpl implements RoomService {
	
	private RoomRepository roomRepository;
	private UserRepository userRepository;
	
	public RoomServiceImpl(RoomRepository roomRepository, UserRepository userRepository) {
		this.roomRepository = roomRepository;
		this.userRepository = userRepository;
	}
	
	
	// This method allows you to create a new room to which the users will be assigned.
	@Override
	public Room createRoom(String roomName, List<User> users) {
		
		if(roomName == null || roomName.equals(""))
			throw new NullPointerException("RoomServiceImpl.createRoom: atribute roomName cannot be null or empty");
					
		Room room = new Room(createRoomId(), roomName);

		users.stream()
				 .filter(u -> u != null)
				 .filter(u -> u.getMongoId() != null)
				 .filter(u -> userRepository.existsById(u.getMongoId()))
				 .filter(u -> u.getRoomsId().add(room.getRoomId()))
				 .peek(u -> room.getUsers().add(u))
				 .peek(u -> userRepository.save(u))
				 .forEach(u -> upd(u, room.getRoomId()));
		
		
		roomRepository.save(room);
					
		return room;
					
	}
	
	private String upd(User user, String roomId) {
				
		Room room;
						
		for(String s: user.getRoomsId()) {
				
			if(!s.equals(roomId)) {
					
			room = roomRepository.findByRoomId(s).orElseThrow(() -> new NoSuchElementException("RoomServiceImpl: Room not found"));
					
			room.getUsers()
				   .stream()
				   .filter(u -> u.getMongoId().equals(user.getMongoId()))
				.forEach(u -> u.getRoomsId().add(roomId));
					   
					  roomRepository.save(room);
			 }
		}
				
		return roomId;
	}
	
	

	// This method allows you to delete a room and all user associations with it.
	// Return transferred Room instance.
	// Throws NullPointerException if the transferred room does not exist.
	// Throws IllegalArgumentException if transferred room is not saved in the database.
	@Override
	public Room deleteRoom(Room room) {
		
		if(room == null) 
			throw new NullPointerException("RoomServiceImpl.deleteRoom: The Room passed to the function cannot be empty.");
			
			
		if(room.getMongoId() == null || !roomRepository.existsById(room.getMongoId()))
			throw new IllegalArgumentException("RoomServiceImpl.deleteRoom: The uploaded object must be assigned an identifier, the mongoId key");
			
			
		List<User> users = room.getUsers();
			
		// removes references to room for all users assigned to transferred (as argument) room
		users.stream()
			 .filter(u -> u != null)
			 .filter(u -> u.getMongoId() != null)
			 .filter(u -> userRepository.existsById(u.getMongoId()))
			 .peek(u -> u.getRoomsId().removeIf(id -> id.equals(room.getRoomId())))	 
			 .peek(u -> updateRoomInfoAboutUsers(u))
			 .forEach(u -> userRepository.save(u));
			
		
		// removes the room
		roomRepository.deleteById(room.getMongoId());
			
		return room;
		
	}
	
	
	@Override
	// This method allows the user to be removed from the chat room, if assigned to him.
	// Returns updated Room instance.
	// Throws NullPointerException if the transferred room does not exist.
	// Throws IllegalArgumentException if transferred room is not saved in the database.
	public Room deleteUserFromRoom(Room room, String nick) throws NullPointerException, IllegalArgumentException {
		
		if(room == null)
			throw new NullPointerException("RoomServiceImpl.deleteUserFromRoom: The Room passed to the function cannot be empty.");
		
		if(room.getMongoId() == null || !roomRepository.existsById(room.getMongoId()))
			throw new IllegalArgumentException("RoomServiceImpl.deleteRoom: The uploaded object must be assigned an identifier, the mongoId key");
		
		
		// removes references to room for all users assigned to transferred (as argument) room.
		room.getUsers().stream()
		  .filter(u -> u != null)
		  .filter(u -> u.getNick().equals(nick))
		  .filter(u -> u.getMongoId() != null)
		  .filter(u -> userRepository.existsById(u.getMongoId()))
		  .peek(u -> u.getRoomsId().removeIf(roomId -> roomId.equals(room.getRoomId())))
		  .peek(u -> userRepository.save(u))
		  .forEach(u -> updateRoomInfoAboutUsers(u));
		
		// removes the user's reference to this room.
		room.getUsers().removeIf(u -> u.getNick().equals(nick));
		roomRepository.save(room);
		
		return room;
	}
	
	
	// The method allows you to update information about the user in the rooms
	// to which the user passed as an argument has a reference in the list of addresses of his rooms.
	private void updateRoomInfoAboutUsers(User user) {
		
		List<Room> rooms = new ArrayList<Room>();
		
		for(String id: user.getRoomsId()) {
			rooms.add(roomRepository.findByRoomId(id).orElseThrow());
		}
		
		for(Room r: rooms) {
			for(User u: r.getUsers()) {
				if(u.getMongoId().equals(user.getMongoId())) {
					u.setRoomsId(user.getRoomsId());
					roomRepository.save(r);
				}
			}
		}
	}
	

	// It works just like deleteUserFromRoom(room, nick) but before that it gets the room instance via its id.
	@Override
	public Room deleteUserFromRoom(String roomId, String nick) throws NullPointerException, IllegalArgumentException, NoSuchElementException {
		
		Room room = roomRepository.findByRoomId(roomId).orElseThrow(() -> new NoSuchElementException());
		
		return deleteUserFromRoom(room, nick);
	}
	
	// Enables you to retrieve a room from the database, otherwise it throws an NoSuchElementException.
	@Override
	public Room getRoom(String roomId)  {
			
		Optional<Room> roomOptional = roomRepository.findByRoomId(roomId);
			
		return roomOptional.orElseThrow(() -> new NoSuchElementException("RoomServiceImpl.getRoom: Room instance not found for the ID " + roomId));
			
	}
	
	@Override
	public List<Room> getUserRooms(User user) throws NullPointerException, UserNotFoundException {
		
		List<Room> rooms = new ArrayList<Room>();
		
		if(user == null)
			throw new NullPointerException();	
		
		if(!userRepository.existsById(user.getMongoId()))
			throw new UserNotFoundException();	
		
		for(String s: user.getRoomsId()) {
			System.out.println("@@@@@@@@@@@@: " + s);
			rooms.add(roomRepository.findByRoomId(s).orElseThrow());
		}
		
//		rooms = user.getRoomsId()
//				.stream()
//				.filter(id -> id != null)
//				.map(id -> roomRepository.findByRoomId(id).orElseThrow())
//				.filter(r -> r.getMongoId() != null)
//				.collect(Collectors.toList());
		
		
		// load pictures
		for(Room r: rooms) {
			for(User u: r.getUsers()) {
				LoadUserProfileImg(u);
			}
		}
		
		return rooms;
		
	}
	
	// Allows you to add a new user to the room.
	@Override
	public Room addUserToRoom(Room room, User user) {
		
		user.getRoomsId().add(room.getRoomId());
		userRepository.save(user);
		
		room.getUsers().add(user);
		roomRepository.save(room);
		
		updateRoomInfoAboutUsers(user);
		
		return room;
	}
	
	// This method allows you to return a unique identifier that can be used when creating a new room
	private String createRoomId() {
								
		String uniqueID;
								
		for(;;) {
			uniqueID = UUID.randomUUID().toString();
					
			try {
				getRoom(uniqueID);
			} catch (NoSuchElementException e) {
				return uniqueID;
			}
						
		}
	}
	
	private User LoadUserProfileImg(User user) {
			
		String pathToImg = user.getPathToProfileImg();
			
		String pictureInBase64 = FileUtils.deserializeObjectAndGetFromDirectory(pathToImg);
		ProfilePicture profilePicture = new ProfilePicture("profile.jpg", "jpg", 0, pictureInBase64);

		user.setProfilePicture(profilePicture);

		return user;
	}

}
