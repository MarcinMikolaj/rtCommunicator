package project.rtc.communicator.messager.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import project.rtc.communicator.messager.dto.Message;

public interface MessageRepository extends MongoRepository<Message, String> {

}
