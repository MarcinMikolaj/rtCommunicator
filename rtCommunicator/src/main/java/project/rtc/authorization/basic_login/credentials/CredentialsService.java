package project.rtc.authorization.basic_login.credentials;

import project.rtc.authorization.basic_login.controllers.pojo.LoginRequestPayload;
import project.rtc.registration.RegistrationRequest;

public interface CredentialsService {
	
	// Allows you to create a new Credentials object for a given user via ReqisterRequest
	public Credentials createCredentialsAndSaveInDatabase(RegistrationRequest reqisterRequest);
	public Credentials setAccountNonLocked(String email, boolean value);
	public boolean exist(LoginRequestPayload loginRequestPayload);
	
}
