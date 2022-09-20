package project.rtc.authorization.basic_login.credentials;

public interface CredentialsRepository {
	
	public Credentials save(Credentials credentials);
	public Credentials update(Credentials credentials);
    public int updatePasswordByEmail(String email, String password);
	public Credentials findByEmail(String email);
	public boolean existByEmail(String email);
	public void createQueryJPQL(String jpql);

}
