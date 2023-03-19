package project.rtc.registration.activateAccountToken.repositories;

import project.rtc.registration.activateAccountToken.dto.ActivateAccountToken;

public interface ActivateAccountTokenRepository {
	
	public ActivateAccountToken create(ActivateAccountToken activateAccountToken);
	public ActivateAccountToken findByToken(String token);
	public int deleteByEmail(String email);

}
