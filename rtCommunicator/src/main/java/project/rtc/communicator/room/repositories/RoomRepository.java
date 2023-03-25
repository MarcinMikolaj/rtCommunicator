package project.rtc.communicator.room.repositories;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import project.rtc.communicator.room.entities.Room;

public interface RoomRepository extends MongoRepository<Room, String> {

	Optional<Room> findByRoomId(String roomId);
	boolean existsByName(String name);
	boolean existsByRoomId(String roomId);
	Room deleteByRoomId(String roomId);
}
