package project.rtc.communicator.room.response_service;

import java.util.List;
import java.util.NoSuchElementException;

import project.rtc.communicator.room.dto.Room;
import project.rtc.communicator.user.dto.User;
import project.rtc.exceptions.UserNotFoundException;

public interface RoomService {
	
	// This method allows you to create a new room to which the users will be assigned.
	public Room createRoom(String roomName, List<User> users);

	// This method allows you to delete a room and all user associations with it.
	// Return transferred Room instance.
	// Throws NullPointerException if the transferred room does not exist.
	// Throws IllegalArgumentException if transferred room is not saved in the database.
	public Room deleteRoom(Room room);
	
	public Room addUserToRoom(Room room, User user);
	
	
	// This method allows the user to be removed from the chat room, if assigned to him.
	// Returns updated Room instance.
	// Throws NullPointerException if the transferred room does not exist.
	// Throws IllegalArgumentException if transferred room is not saved in the database.
	public Room deleteUserFromRoom(Room room, String nick) throws NullPointerException, IllegalArgumentException;
	
	// It works just like deleteUserFromRoom(room, nick) but before that it gets the room instance via its id.
	public Room deleteUserFromRoom(String roomId, String nick) throws NullPointerException, IllegalArgumentException, NoSuchElementException;
	
	
	
	// Enables you to retrieve a room from the database, otherwise it throws an NoSuchElementException.
	public Room getRoom(String roomId);
	
	public List<Room> getUserRooms(User user) throws NullPointerException, UserNotFoundException;
	
}
