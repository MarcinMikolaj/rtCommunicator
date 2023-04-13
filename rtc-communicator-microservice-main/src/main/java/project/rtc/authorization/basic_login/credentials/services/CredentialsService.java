package project.rtc.authorization.basic_login.credentials.services;

import project.rtc.authorization.basic_login.dto.LoginRequestPayload;
import project.rtc.authorization.basic_login.credentials.entities.Credentials;
import project.rtc.registration.dto.RegistrationRequestDto;

public interface CredentialsService {
	Credentials create(RegistrationRequestDto dto, boolean isAccountNonLocked);
	Credentials setAccountNonLocked(String email, boolean value);
	boolean exist(LoginRequestPayload loginRequestPayload);
	int updatePasswordByEmail(String userEmail, String password);
	
}
