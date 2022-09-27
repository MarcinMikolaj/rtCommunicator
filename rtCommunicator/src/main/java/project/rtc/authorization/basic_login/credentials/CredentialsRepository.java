package project.rtc.authorization.basic_login.credentials;

public interface CredentialsRepository {
	
	public Credentials save(Credentials credentials);
	public Credentials update(Credentials credentials);
	
	// Return the number of entities updated.
	public int updateEmailById(String email, Long id);
	public int updatePasswordById(String password, Long id);
    public int updatePasswordByEmail(String email, String password);
    
    public Credentials findById(Long id);
	public Credentials findByEmail(String email);
	
	public boolean existByEmail(String email);
	
	public void createQueryJPQL(String jpql);
	public Credentials deleteById(Long id);
	
	

}
 