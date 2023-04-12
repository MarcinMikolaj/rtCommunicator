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

	@Value("${app.registration.security.activate-account-by-email-expire-time}")
	private long expiryTime;

	public ActivateAccountToken assignNewTokenToAccount(String email) {
		Date issuedAt = new Date(System.currentTimeMillis());
		Date expiration = new Date(System.currentTimeMillis() + expiryTime);

		ActivateAccountToken activateAccountToken = new ActivateAccountToken(email
				, JwtTokenProvider.create(jwtSecretKey, email, issuedAt, expiration)
				, new Timestamp(expiration.getTime())
				, new Timestamp(expiration.getTime()));
		return activateAccountTokenRepository.create(activateAccountToken);
	}

	public ActivateAccountToken findByToken(String token) {return activateAccountTokenRepository.findByToken(token);}
	public void delete(String email) {
		activateAccountTokenRepository.deleteByEmail(email);
	}

}
