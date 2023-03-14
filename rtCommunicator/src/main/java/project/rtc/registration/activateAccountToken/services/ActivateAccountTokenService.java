package project.rtc.registration.activateAccountToken.services;

import java.sql.Timestamp;
import java.util.Date;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import project.rtc.registration.activateAccountToken.dto.ActivateAccountToken;
import project.rtc.registration.activateAccountToken.repositories.ActivateAccountTokenRepository;
import project.rtc.registration.activateAccountToken.repositories.impl.ActivateAccountTokenRepositoryImpl;
import project.rtc.utils.jwt.JwtTokenProvider;

@Service
@RequiredArgsConstructor
public class ActivateAccountTokenService {
	
	private final ActivateAccountTokenRepository activateAccountTokenRepository;
	private final JwtTokenProvider jwtTokenProvider;
	

	public ActivateAccountToken assignNewTokenToAccount(String email) {
		
		String authorizationToken;
		ActivateAccountToken activateAccountToken;
		
		Long expirationTimeInMilis = (long) 1800000;
		Long currentTimeInMili = System.currentTimeMillis();
		Date issuedAt = new Date(currentTimeInMili);
		Date expiration = new Date(currentTimeInMili + expirationTimeInMilis);
		
		authorizationToken = jwtTokenProvider.createJwtToken(email, issuedAt, expiration);
		
		Timestamp expirationAsTimestamp = new Timestamp(expiration.getTime());
		Timestamp issuedAtAsTimestamp = new Timestamp(expiration.getTime());
		
		activateAccountToken = new ActivateAccountToken(email, authorizationToken, issuedAtAsTimestamp, expirationAsTimestamp);
		activateAccountTokenRepository.create(activateAccountToken);
		
		return  activateAccountToken;
	}
	
	
	public ActivateAccountToken findByToken(String token) {
		
		ActivateAccountToken activateAccountToken;
		
		activateAccountToken = activateAccountTokenRepository.findByToken(token);
		
		return activateAccountToken;
	}
	
	public void delete(String email) {
		activateAccountTokenRepository.deleteByEmail(email);
	}

}
