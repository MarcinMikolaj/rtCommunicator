package project.rtc.communicator.messager.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import project.rtc.communicator.messager.entities.Message;

import java.util.List;
import java.util.Optional;

public interface MessageRepository extends MongoRepository<Message, String> {

    Optional<List<Message>> findAllByRoomId(String roomId);
}
