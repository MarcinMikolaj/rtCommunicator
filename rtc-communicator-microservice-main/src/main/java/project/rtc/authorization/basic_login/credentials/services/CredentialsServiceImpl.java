package project.rtc.authorization.basic_login.credentials.services;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import project.rtc.authorization.basic_login.controllers.dto.LoginRequestPayload;
import project.rtc.authorization.basic_login.credentials.entities.Credentials;
import project.rtc.authorization.basic_login.credentials.repositories.CredentialsRepository;
import project.rtc.registration.dto.RegistrationRequestDto;

@Service
@RequiredArgsConstructor
public class CredentialsServiceImpl implements CredentialsService {
	
	private final CredentialsRepository credentialsRepository;
	private final PasswordEncoder passwordEncoder;
	

	@Override
	public boolean exist(LoginRequestPayload loginRequestPayload) {
		
		Credentials credentials = credentialsRepository.findByEmail(loginRequestPayload.getEmail());
		
		if(credentials != null) {
			return passwordEncoder.matches(loginRequestPayload.getPassword(), credentials.getPassword());
		}
	
		return false;
	}
	

	@Override
	public Credentials createCredentialsAndSaveInDatabase(RegistrationRequestDto reqisterRequest) {
		
		Credentials credentials = new Credentials();
		credentials.setEmail(reqisterRequest.getEmail());
		credentials.setPassword(passwordEncoder.encode(reqisterRequest.getPassword()));
		credentials.setProvider(reqisterRequest.getProvider());
//		credentials.setIsAccountNonLocked(false); //only for test
		credentials.setIsAccountNonLocked(true);
		
		Credentials savedCredentials = credentialsRepository.save(credentials);
			
		return savedCredentials;
	}

	@Override
	public Credentials setAccountNonLocked(String email, boolean value) {
		
		Credentials credentials = credentialsRepository.findByEmail(email);
		credentials.setIsAccountNonLocked(value);
		
		Credentials updatedCredentials = credentialsRepository.update(credentials);
		
		return updatedCredentials;
	}
	
	

	@Override
	public int updatePasswordByEmail(String userEmail, String password) {
		
		return credentialsRepository.updatePasswordByEmail(userEmail, passwordEncoder.encode(password));
	}

}
