package project.rtc.authorization.forgot_password.services.impl;

import java.sql.Timestamp;
import java.util.Date;

import javax.mail.MessagingException;
import javax.persistence.NoResultException;
import javax.servlet.http.HttpServletResponse;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import project.rtc.authorization.basic_login.credentials.repositories.CredentialsRepository;
import project.rtc.authorization.forgot_password.entities.PasswordResetToken;
import project.rtc.authorization.forgot_password.repositories.ResetPasswordTokenRepository;
import project.rtc.authorization.forgot_password.services.ForgotPasswordService;
import project.rtc.infrastructure.exception.exceptions.InvalidTokenException;
import project.rtc.infrastructure.utils.token.JwtTokenProvider;
import project.rtc.infrastructure.utils.mail.MailSenderService;

@Service
@RequiredArgsConstructor
public class ForgotPasswordServiceImpl implements ForgotPasswordService {

	private final MailSenderService mailSenderService;
	private final CredentialsRepository credentialsRepository;
	private final ResetPasswordTokenRepository resetPasswordTokenRepository;
	private final PasswordEncoder passwordEncoder;

	@Value("${app.security.reset-password.jwt.expiry-time}")
	private long expiryTimeForResetPasswordToken;
	@Value("${app.security.jwt.secret_key}")
	private String jwtSecretKey;

	public PasswordResetToken startTheProcessOfChangingThePassword(String email, HttpServletResponse response) throws MessagingException {
		try {
			credentialsRepository.findByEmail(email);
		} catch (NoResultException e) {
			return null;
		}

		PasswordResetToken resetPasswordToken = assignNewToken(email);
		String link = "http://localhost:8080/app/forgot/password/tk" + "?token=" + resetPasswordToken.getToken();
		mailSenderService.sendMessage(email, "rtCommunicator", prepareMessageText(link));
		return resetPasswordToken;
	}

	public PasswordResetToken changePasswordIfTokenIsCorrect(String tokenFromUrl, String password) throws InvalidTokenException {
		PasswordResetToken resetPasswordToken = resetPasswordTokenRepository.findByToken(tokenFromUrl);

		// If token expired.
		if(JwtTokenProvider.getTokenExpiration(jwtSecretKey, resetPasswordToken.getToken()).before(new Date()))
			throw new InvalidTokenException("Token reset password expired: " + resetPasswordToken);

		// If token not equal.
		if(!resetPasswordToken.getToken().equals(tokenFromUrl))
			throw new InvalidTokenException("Token reset is not equal !: " + resetPasswordToken);

		String email = JwtTokenProvider.getTokenSubject(jwtSecretKey, tokenFromUrl);
		credentialsRepository.updatePasswordByEmail(email,passwordEncoder.encode(password));
		resetPasswordTokenRepository.removeByToken(tokenFromUrl);
		return resetPasswordToken;
	}

	// Enables assigning a new object of the PasswordResetToken.class type to a given user
	// , which after being created is saved in the database.
	// If the assignment action fails, null is returned.
	private PasswordResetToken assignNewToken(String email) {
		Date issuedAt = new Date(System.currentTimeMillis());
		Date expiration = new Date(System.currentTimeMillis() + expiryTimeForResetPasswordToken);
		PasswordResetToken resetPasswordToken = new PasswordResetToken(email
				, JwtTokenProvider.create(jwtSecretKey, email, issuedAt, expiration)
				, new Timestamp(issuedAt.getTime())
				, new Timestamp(expiration.getTime()));
		return resetPasswordTokenRepository.save(resetPasswordToken);
	}

	private String prepareMessageText(String link){
		return "Hello Marcin!\r\n"
				+ "We received a request to reset your rtCommunicator password.\r\n"
				+ "Enter the following password reset code: " + link;
	}
	
}
