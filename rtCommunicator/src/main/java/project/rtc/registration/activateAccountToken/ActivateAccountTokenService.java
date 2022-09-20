package project.rtc.registration.activateAccountToken;

import java.sql.Timestamp;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import project.rtc.utils.jwt.JwtTokenProvider;

@Service
public class ActivateAccountTokenService {
	
	private ActivateAccountTokenRepository activateAccountTokenRepository;
	private JwtTokenProvider jwtTokenProvider;
	
	@Autowired
	public void setActivateAccountTokenService(ActivateAccountTokenRepositoryImpl activateAccountTokenRepositoryImpl) {
		this.activateAccountTokenRepository = activateAccountTokenRepositoryImpl;
	}
	
	@Autowired
	public void setJwtTokenProvider(JwtTokenProvider jwtTokenProvider) {
		this.jwtTokenProvider = jwtTokenProvider;
	}
	
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
