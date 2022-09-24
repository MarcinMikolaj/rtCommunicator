package project.rtc.authorization.forgot_password;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.Date;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import project.rtc.authorization.basic_login.credentials.Credentials;
import project.rtc.authorization.basic_login.credentials.CredentialsRepository;
import project.rtc.authorization.basic_login.credentials.CredentialsRepositoryImpl;
import project.rtc.authorization.forgot_password.reset_password_token.PasswordResetToken;
import project.rtc.authorization.forgot_password.reset_password_token.ResetPasswordTokenRepository;
import project.rtc.authorization.forgot_password.reset_password_token.ResetPasswordTokenRepositoryImpl;
import project.rtc.utils.ConsoleColors;
import project.rtc.utils.jwt.JwtTokenProvider;
import project.rtc.utils.mail.MailSenderService;
import project.rtc.utils.mail.MailSenderServiceImpl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class ForgotPasswordService {
	
	private JwtTokenProvider jwtTokenProvider;
	private MailSenderService mailSenderService;
	private CredentialsRepository credentialsRepository;
	private ResetPasswordTokenRepository resetPasswordTokenRepository;
	private PasswordEncoder passwordEncoder;
	private static final Logger logger = LoggerFactory.getLogger(ForgotPasswordService.class);
	
	@Autowired
	public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
		this.passwordEncoder = passwordEncoder;
	}
	
	@Autowired
	public void setJwtTokenProvider(JwtTokenProvider jwtTokenProvider) {
		this.jwtTokenProvider = jwtTokenProvider;
	}
	
	@Autowired
	public void setResetPasswordTokenRepository(ResetPasswordTokenRepositoryImpl resetPasswordTokenRepositoryImpl) {
		this.resetPasswordTokenRepository = resetPasswordTokenRepositoryImpl;
	}
	
	
	@Autowired
	public void setMailSenderService(MailSenderServiceImpl mailSenderServiceImpl) {
		this.mailSenderService = mailSenderServiceImpl;
	}
	
	@Autowired
	public void setCredentialsRepository(CredentialsRepositoryImpl credentialsRepositoryImpl) {
	   this.credentialsRepository = credentialsRepositoryImpl;
	}
	
	
	// This method is called to begin the password change process for the user.
    // Allows you to assign a token for a given account for which the password is to be updated.
	// And it sends to the user's e-mail address a link redirecting to enter a new password
	public boolean startTheProcessOfChangingThePassword(String email, HttpServletResponse response) throws IOException {
		
		Credentials credentials = credentialsRepository.findByEmail(email);
		
		if(credentials == null) {
			return true;
		}
		
		PasswordResetToken resetPasswordToken = assignNewToken(email, 1800000);
		
		String link = "http://localhost:8080/app/forgot/password/tk/" + "?token=" + resetPasswordToken.getToken();
		
		String subject = "rtCommunicator";
		String text = "Hello Marcin!\r\n"
				+ "We received a request to reset your rtCommunicator password.\r\n"
				+ "Enter the following password reset code: " + link;
		
		try {
			mailSenderService.sendMessage(email, subject, text);
			return true;
		} catch (MessagingException e) {
			System.out.println("ForgotPasswordService.send: The message could not be sent, mailSenderService problem");
			return false;
		} 
	}
	
	
	// Enables assigning a new object of the PasswordResetToken.class type to a given user, which after being created is saved in the database.
	// If the assignment action fails, null is returned
    private PasswordResetToken assignNewToken(String email, long expirationTimeInMilis) {
    	
    	if(expirationTimeInMilis <= 0) {
    		throw new IllegalArgumentException(
    				ConsoleColors.RED 
    				+ "ForgotPasswordService.assignNewToken: expirationTimeInMilis cannot be <= 0"
    				+ ConsoleColors.RESET);
    	}
		
		Long currentTimeInMili = System.currentTimeMillis();
		
		Date issuedAt = new Date(currentTimeInMili);
		Date expitation = new Date(currentTimeInMili + expirationTimeInMilis);
		
		
		String token = jwtTokenProvider.createJwtToken(email, issuedAt, expitation);
		
		PasswordResetToken resetPasswordToken = new PasswordResetToken();
		resetPasswordToken.setEmail(email);
		resetPasswordToken.setToken(token);
		resetPasswordToken.setCreate_on(new Timestamp(issuedAt.getTime()));
		resetPasswordToken.setExpiration_time(new Timestamp(expitation.getTime()));
		
		PasswordResetToken savedResetPasswordToken = resetPasswordTokenRepository.save(resetPasswordToken);
		
		if(savedResetPasswordToken != null)
			return resetPasswordToken;
		
		
		return null;
	}
	
	// If the token provided by the user is correct (i.e. it matches the token assigned to the user and has not expired), the password change operation is started
    // If the token is incorrect, it stops the password change process
	public boolean changePasswordIfTokenIsCorrect(String tokenFromUrl, String password) {
		
		PasswordResetToken resetPasswordToken = resetPasswordTokenRepository.findByToken(tokenFromUrl);
		
		if(resetPasswordToken == null) 
			return false;
		
		
		Date currentDate = new Date();
		
		if(jwtTokenProvider.getTokentExpiration(resetPasswordToken.getToken()).before(currentDate)) {
			logger.info("ForgotPasswordService.changePasswordIfTokenIsCorrect: token for user: "
		+ jwtTokenProvider.getTokenSubject(tokenFromUrl)
		+ " expired");
			
			return false;
		}
		
		if(resetPasswordToken.getToken().equals(tokenFromUrl)) {
			String email = jwtTokenProvider.getTokenSubject(tokenFromUrl);
			credentialsRepository.updatePasswordByEmail(email,passwordEncoder.encode(password));
			resetPasswordTokenRepository.removeByToken(tokenFromUrl);
	        return true;
		}
		
		return false;
	}
	
}
