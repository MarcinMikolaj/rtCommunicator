package project.rtc.communicator.messager.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import project.rtc.communicator.messager.entities.Message;

public interface MessageRepository extends MongoRepository<Message, String> {

}
