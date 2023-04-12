package project.rtc.authorization.basic_login.credentials.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import project.rtc.authorization.basic_login.controllers.dto.LoginRequestPayload;
import project.rtc.authorization.basic_login.credentials.entities.Credentials;
import project.rtc.authorization.basic_login.credentials.repositories.CredentialsRepository;
import project.rtc.authorization.basic_login.credentials.services.CredentialsService;
import project.rtc.registration.dto.RegistrationRequestDto;

@Service
@RequiredArgsConstructor
public class CredentialsServiceImpl implements CredentialsService {
	
	private final CredentialsRepository credentialsRepository;
	private final PasswordEncoder passwordEncoder;

	@Override
	public boolean exist(LoginRequestPayload loginRequestPayload) {
		Credentials credentials = credentialsRepository.findByEmail(loginRequestPayload.getEmail());
		if(credentials != null)
			return passwordEncoder.matches(loginRequestPayload.getPassword(), credentials.getPassword());
		return false;
	}

	@Override
	public Credentials create(RegistrationRequestDto dto, boolean isAccountNonLocked) {
		Credentials credentials = new Credentials();
		credentials.setEmail(dto.getEmail());
		credentials.setPassword(passwordEncoder.encode(dto.getPassword()));
		credentials.setProvider(dto.getProvider());
		credentials.setIsAccountNonLocked(isAccountNonLocked);
		return credentialsRepository.save(credentials);
	}

	@Override
	public Credentials setAccountNonLocked(String email, boolean value) {
		Credentials credentials = credentialsRepository.findByEmail(email);
		credentials.setIsAccountNonLocked(value);
		return credentialsRepository.update(credentials);
	}

	@Override
	public int updatePasswordByEmail(String userEmail, String password) {
		return credentialsRepository.updatePasswordByEmail(userEmail, passwordEncoder.encode(password));
	}

}
