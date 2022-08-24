package project.rtc.authorization.basic_login.credentials;

import project.rtc.authorization.basic_login.controllers.pojo.LoginPayloadRequest;

public interface CredentialsService {
	
	public boolean checkIfCredentialsExist(LoginPayloadRequest loginPayloadRequest);

	
}
