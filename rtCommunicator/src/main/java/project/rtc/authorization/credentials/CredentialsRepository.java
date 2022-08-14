package project.rtc.authorization.credentials;

import java.util.Optional;

public interface CredentialsRepository {
	
    // It allows you to save an instance of the Credentials.Class to a connected database
	// Returns true on success, false otherwise
	public Credentials save(Credentials credentials);
	
	 // It allows you to update an instance of the Credentials.Class
	public boolean update(Credentials credentials);
	
	// Find Credentials object in data base by email
	public Credentials findByEmail(String email);

	// Allows you to create a query to the database
	public void createQueryJPQL(String jpql);

}
