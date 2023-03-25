package project.rtc.authorization.basic_login.credentials.services;

import project.rtc.authorization.basic_login.controllers.dto.LoginRequestPayload;
import project.rtc.authorization.basic_login.credentials.entities.Credentials;
import project.rtc.registration.dto.RegistrationRequestDto;

public interface CredentialsService {
	
	// Allows you to create a new Credentials object for a given user via ReqisterRequest
	public Credentials createCredentialsAndSaveInDatabase(RegistrationRequestDto reqisterRequest);
	
	public Credentials setAccountNonLocked(String email, boolean value);
	public boolean exist(LoginRequestPayload loginRequestPayload);
	
	public int updatePasswordByEmail(String userEmail, String password);
	
}
