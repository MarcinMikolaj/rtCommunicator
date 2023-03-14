package project.rtc.authorization.basic_login.credentials.services;

import project.rtc.authorization.basic_login.controllers.dto.LoginRequestPayload;
import project.rtc.authorization.basic_login.credentials.dto.Credentials;
import project.rtc.registration.dto.RegistrationRequest;

public interface CredentialsService {
	
	// Allows you to create a new Credentials object for a given user via ReqisterRequest
	public Credentials createCredentialsAndSaveInDatabase(RegistrationRequest reqisterRequest);
	
	public Credentials setAccountNonLocked(String email, boolean value);
	public boolean exist(LoginRequestPayload loginRequestPayload);
	
	public int updatePasswordByEmail(String userEmail, String password);
	
}
