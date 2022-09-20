package project.rtc.communicator.room;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface RoomRepository extends MongoRepository<Room, String> {
	
	public Room findByRoomId(String roomId);

}
