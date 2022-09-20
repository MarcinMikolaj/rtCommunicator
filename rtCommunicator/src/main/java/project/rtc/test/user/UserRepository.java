package project.rtc.test.user;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, String> {

	User findByNick(String nick);
	Optional<User> findByEmail(String email);
	boolean existsByNick(String nick);
	
}
