package project.rtc.authorization.basic_login.credentials;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import project.rtc.authorization.basic_login.controllers.pojo.LoginPayloadRequest;

@Service
public class CredentialsServiceImpl implements CredentialsService {
	
	private CredentialsRepository credentialsRepository;
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	public void setCredentialsRepository(CredentialsRepositoryImpl credentialsRepositoryImpl) {
		this.credentialsRepository = credentialsRepositoryImpl;
	}
	
	@Autowired
	public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
		this.passwordEncoder = passwordEncoder;
	}
	

	@Override
	public boolean checkIfCredentialsExist(LoginPayloadRequest loginPayloadRequest) {
		
		Credentials credentials = credentialsRepository.findByEmail(loginPayloadRequest.getEmail());
		
		if(credentials != null) {
			return passwordEncoder.matches(loginPayloadRequest.getPassword(), credentials.getPassword());
		}
	
		return false;
	}

}
