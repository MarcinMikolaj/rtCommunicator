package project.rtc.communicator.messager.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import project.rtc.communicator.messager.entities.Message;

import java.util.List;
import java.util.Optional;

public interface MessageRepository extends MongoRepository<Message, String> {

    Optional<List<Message>> findAllByRoomId(String roomId);
    Optional<Page<Message>> findAllByRoomIdOrderByCreationTimeInMillisecondsUTCDesc(String roomId, Pageable pageable);
    @Query(value = "{'roomId': ?0, 'userId': {$ne: ?1}, 'missedBy': ?1}")
    Optional<List<Message>> findAllUnreadMessagesInARoom(String roomId, String userId);

}
