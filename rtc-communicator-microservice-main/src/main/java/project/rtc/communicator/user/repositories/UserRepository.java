package project.rtc.communicator.user.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import project.rtc.communicator.user.entities.User;

public interface UserRepository extends MongoRepository<User, String> {
	Optional<User> findByNick(String nick);
	Optional<User> findByEmail(String email);
	Optional<User> findByUserId(String userId);
	boolean existsByNick(String nick);
	@Modifying
	@Transactional
	@Query("UPDATE User u SET u.roomsId = :roomsId WHERE u.mongoId = :mongoId")
	int updateRoomIdList(@Param("mongoId") String mongoId, @Param("roomsId") List<String> roomsId);
	
}
