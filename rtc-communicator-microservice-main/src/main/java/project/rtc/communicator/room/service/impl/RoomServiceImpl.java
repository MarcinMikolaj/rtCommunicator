package project.rtc.communicator.room.service.impl;

import java.util.*;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import project.rtc.communicator.room.repositories.RoomRepository;
import project.rtc.communicator.room.entities.Room;
import project.rtc.communicator.room.service.RoomService;
import project.rtc.communicator.user.entities.User;
import project.rtc.communicator.user.repositories.UserRepository;
import project.rtc.infrastructure.exception.exceptions.RoomNotFoundException;
import project.rtc.infrastructure.exception.exceptions.UserNotFoundException;
import project.rtc.registration.dto.ProfilePicture;
import project.rtc.infrastructure.utils.FileUtils;

@Service
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
@RequiredArgsConstructor
public class RoomServiceImpl implements RoomService {
	
	private final RoomRepository roomRepository;
	private final UserRepository userRepository;

	public Room create(String name, String userId) throws UserNotFoundException {
		String roomId = generateUniqueId();
		grantAccessUserToRoom(userId, roomId);
		return roomRepository.save(new Room(roomId, name, Collections.singletonList(userId)));
	}

	public Room create(String name, List<String> usersId) throws UserNotFoundException {
		String roomId = generateUniqueId();
		for(String id: usersId)
			grantAccessUserToRoom(id, roomId);
		return roomRepository.save(new Room(roomId, name, usersId));
	}

	public Room getRoom(String roomId) throws RoomNotFoundException {
		return roomRepository.findByRoomId(roomId).orElseThrow(RoomNotFoundException::new);
	}

	public Room removeRoom(String roomId) throws UserNotFoundException, RoomNotFoundException {
		List<String> usersId = roomRepository.findByRoomId(roomId).orElseThrow(RoomNotFoundException::new).getUsersId();
		denyUsersAccessToRoom(roomId, usersId);
		return roomRepository.deleteByRoomId(roomId);
	}

	public Room removeUserFromRoom(String roomId, String userId) throws RoomNotFoundException, UserNotFoundException {
		Room room = roomRepository.findByRoomId(roomId).orElseThrow(RoomNotFoundException::new);
		denyUsersAccessToRoom(room.getRoomId(), room.getUsersId());
		room.getUsersId().removeIf(id -> id.equals(userId));
		return roomRepository.save(room);
	}

	public Room addUserToRoom(String roomId, String userId) throws UserNotFoundException, RoomNotFoundException {
		Room room = roomRepository.findByRoomId(roomId).orElseThrow(RoomNotFoundException::new);
		grantAccessUserToRoom(userId, roomId);
		room.getUsersId().add(userId);
		return roomRepository.save(room);
	}

	private User grantAccessUserToRoom(String userId, String roomId) throws UserNotFoundException {
		User user = userRepository.findByUserId(userId).orElseThrow(UserNotFoundException::new);
		user.getRoomsId().add(roomId);
		return userRepository.save(user);
	}

	private List<String> denyUsersAccessToRoom(String roomId, List<String> usersId) throws UserNotFoundException, RoomNotFoundException {
		List<User> users = getUsersFromRoom(roomId, false);
		users.stream()
				.filter(Objects::nonNull)
				.filter(u -> u.getMongoId() != null)
				.filter(u -> u.getRoomsId().contains(roomId))
				.peek(u -> u.getRoomsId().removeIf(id -> id.equals(roomId)))
				.forEach(u -> userRepository.save(u));
		return usersId;
	}

	public List<User> getUsersFromRoom(String roomId, boolean withPicture) throws RoomNotFoundException {
		Room room = roomRepository.findByRoomId(roomId).orElseThrow(RoomNotFoundException::new);
		List<User> users = new ArrayList<>();
		room.getUsersId().forEach(id -> users.add(userRepository.findByUserId(id).orElseThrow()));

		if(withPicture)
			for(User u: users)
				LoadUserProfileImg(u);

		return users;
	}

	public List<Room> getAllUserRooms(String userId) throws UserNotFoundException {
		List<Room> rooms = new ArrayList<>();
		User user = userRepository.findByUserId(userId).orElseThrow(UserNotFoundException::new);
		user.getRoomsId().forEach(id -> rooms.add(roomRepository.findByRoomId(id).orElseThrow()));
		return rooms;
	}

	public Room updateRoomName(String roomId, String name) throws RoomNotFoundException {
		Room room = roomRepository.findByRoomId(roomId).orElseThrow(RoomNotFoundException::new);
		room.setName(name);
		return roomRepository.save(room);
	}

	private User LoadUserProfileImg(User user) {
		String pathToImg = user.getPathToProfileImg();
		String pictureInBase64 = FileUtils.deserializeObjectAndGetFromDirectory(pathToImg);
		ProfilePicture profilePicture = new ProfilePicture("profile.jpg", "jpg", 0, pictureInBase64);
		user.setProfilePicture(profilePicture);
		return user;
	}

	private String generateUniqueId() {return UUID.randomUUID().toString();}

}
