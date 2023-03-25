package project.rtc.registration.activateAccountToken.repositories;

import project.rtc.registration.activateAccountToken.entities.ActivateAccountToken;

public interface ActivateAccountTokenRepository {
	
	ActivateAccountToken create(ActivateAccountToken activateAccountToken);
	ActivateAccountToken findByToken(String token);
	int deleteByEmail(String email);

}
