package project.rtc.registration.activateAccountToken.services.impl;

import java.sql.Timestamp;
import java.util.Date;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import project.rtc.infrastructure.utils.token.JwtTokenProvider;
import project.rtc.registration.activateAccountToken.entities.ActivateAccountToken;
import project.rtc.registration.activateAccountToken.repositories.ActivateAccountTokenRepository;
import project.rtc.registration.activateAccountToken.services.ActivateAccountTokenService;

@Service
@RequiredArgsConstructor
public class ActivateAccountTokenServiceImpl implements ActivateAccountTokenService {
	private final ActivateAccountTokenRepository activateAccountTokenRepository;
	@Value("${app.security.jwt.secret_key}")
	private String jwtSecretKey;

	public ActivateAccountToken assignNewTokenToAccount(String email) {
		
		String authorizationToken;
		ActivateAccountToken activateAccountToken;
		
		Long expirationTimeInMilis = (long) 1800000;
		Long currentTimeInMili = System.currentTimeMillis();
		Date issuedAt = new Date(currentTimeInMili);
		Date expiration = new Date(currentTimeInMili + expirationTimeInMilis);
		
		authorizationToken = JwtTokenProvider.create(jwtSecretKey, email, issuedAt, expiration);
		
		Timestamp expirationAsTimestamp = new Timestamp(expiration.getTime());
		Timestamp issuedAtAsTimestamp = new Timestamp(expiration.getTime());
		
		activateAccountToken = new ActivateAccountToken(email, authorizationToken, issuedAtAsTimestamp, expirationAsTimestamp);
		activateAccountTokenRepository.create(activateAccountToken);
		return  activateAccountToken;
	}
	
	
	public ActivateAccountToken findByToken(String token) {return activateAccountTokenRepository.findByToken(token);}
	
	public void delete(String email) {
		activateAccountTokenRepository.deleteByEmail(email);
	}

}
