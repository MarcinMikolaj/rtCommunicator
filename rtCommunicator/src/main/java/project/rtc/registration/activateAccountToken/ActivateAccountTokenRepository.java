package project.rtc.registration.activateAccountToken;

public interface ActivateAccountTokenRepository {
	
	public ActivateAccountToken create(ActivateAccountToken activateAccountToken);
	public ActivateAccountToken findByToken(String token);
	public int deleteByEmail(String email);

}
