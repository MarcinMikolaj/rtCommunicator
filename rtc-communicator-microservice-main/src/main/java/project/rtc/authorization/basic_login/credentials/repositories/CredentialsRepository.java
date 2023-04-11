package project.rtc.authorization.basic_login.credentials.repositories;

import project.rtc.authorization.basic_login.credentials.entities.Credentials;

public interface CredentialsRepository {
	
	Credentials save(Credentials credentials);
	Credentials update(Credentials credentials);
	int updateEmailById(String email, Long id);
	int updatePasswordById(String password, Long id);
    int updatePasswordByEmail(String email, String password);
    Credentials findById(Long id);
	Credentials findByEmail(String email);
	boolean existByEmail(String email);
	void createQueryJPQL(String jpql);
	Credentials deleteById(Long id);
	int deleteByEmail(String email);

}
 