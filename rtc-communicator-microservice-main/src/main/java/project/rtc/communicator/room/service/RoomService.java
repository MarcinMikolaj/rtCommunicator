package project.rtc.communicator.room.service;

import java.util.List;

import project.rtc.communicator.room.entities.Room;
import project.rtc.communicator.user.entities.User;
import project.rtc.infrastructure.exception.exceptions.RoomNotFoundException;
import project.rtc.infrastructure.exception.exceptions.UserNotFoundException;

public interface RoomService {

	Room create(String name, String userId) throws UserNotFoundException;
	Room create(String name, List<String> usersId) throws UserNotFoundException;
	Room getRoom(String roomId) throws RoomNotFoundException;
	Room removeRoom(String roomId) throws UserNotFoundException, RoomNotFoundException;
	Room removeUserFromRoom(String roomId, String userId) throws RoomNotFoundException, UserNotFoundException;
	Room addUserToRoom(String roomId, String userId) throws UserNotFoundException, RoomNotFoundException;
	List<User> getUsersFromRoom(String roomId, boolean withPicture) throws RoomNotFoundException;
	List<Room> getAllUserRooms(String userId) throws UserNotFoundException, RoomNotFoundException;
	Room updateRoomName(String roomId, String name) throws RoomNotFoundException;
	
}
