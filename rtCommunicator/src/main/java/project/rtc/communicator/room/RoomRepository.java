package project.rtc.communicator.room;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface RoomRepository extends MongoRepository<Room, String> {
	

	public Optional<Room> findByRoomId(String roomId);

}
