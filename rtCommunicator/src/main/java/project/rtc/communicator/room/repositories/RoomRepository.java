package project.rtc.communicator.room.repositories;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import project.rtc.communicator.room.dto.Room;

public interface RoomRepository extends MongoRepository<Room, String> {
	

	public Optional<Room> findByRoomId(String roomId);

}
