package project.rtc.communicator.user.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import project.rtc.communicator.user.dto.User;

public interface UserRepository extends MongoRepository<User, String> {

	public Optional<User> findByNick(String nick);
	public Optional<User> findByEmail(String email);
	public boolean existsByNick(String nick);
	
	@Modifying
	@Transactional
	@Query("UPDATE User u SET u.roomsId = :roomsId WHERE u.mongoId = :mongoId")
	public int updateRoomIdList(@Param("mongoId") String mongoId, @Param("roomsId") List<String> roomsId);
	
}
