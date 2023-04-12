package project.rtc.registration.services.impl;

import java.util.Date;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import project.rtc.authorization.basic_login.credentials.services.CredentialsService;
import project.rtc.communicator.user.entities.User;
import project.rtc.communicator.user.services.impl.UserServiceImpl;
import project.rtc.infrastructure.exception.exceptions.InvalidTokenException;
import project.rtc.infrastructure.utils.token.JwtTokenProvider;
import project.rtc.registration.activateAccountToken.entities.ActivateAccountToken;
import project.rtc.registration.activateAccountToken.services.impl.ActivateAccountTokenServiceImpl;
import project.rtc.registration.dto.RegistrationRequestDto;
import project.rtc.registration.services.RegistrationService;
import project.rtc.infrastructure.utils.mail.MailSenderService;

@Service
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
@RequiredArgsConstructor
public class RegistrationServiceImpl implements RegistrationService {
	
	private final CredentialsService credentialsService;
    private final ActivateAccountTokenServiceImpl activateAccountTokenServiceImpl;
    private final MailSenderService mailSenderService;
	private final UserServiceImpl userService;
	@Value("${app.security.jwt.secret_key}")
	private String jwtSecretKey;
	@Value("${app.registration.security.active-account-via-email}")
	private boolean activateAccountByEmailOption;

	public User registerAccount(RegistrationRequestDto dto) throws MessagingException {
		if(activateAccountByEmailOption){
			credentialsService.create(dto, false);
			sendActivateAccountLinkToEmail(dto.getEmail());
		} else
			credentialsService.create(dto, true);
		return userService.create(dto.getNick(), dto.getEmail(), dto.getPicture());
	}
	
	private void sendActivateAccountLinkToEmail(String email) throws MessagingException {
		ActivateAccountToken activateAccountToken = activateAccountTokenServiceImpl.assignNewTokenToAccount(email);
		String link = "http://localhost:8080/app/registration/activate" + "?token=" + activateAccountToken.getToken();
		mailSenderService.sendMessage(email, "Activate Link", prepareText(link));
	}

	public boolean activateAccount(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse)
			throws InvalidTokenException {
		String token = httpServletRequest.getParameter("token");
		ActivateAccountToken activateAccountToken = activateAccountTokenServiceImpl.findByToken(token);
		String email = JwtTokenProvider.getTokenSubject(jwtSecretKey, token);

		// Check tokens are equal and token not expired.
		if(JwtTokenProvider.getTokenExpiration(jwtSecretKey, token).before(new Date())
				|| !activateAccountToken.getToken().equals(token))
			throw new InvalidTokenException();

		credentialsService.setAccountNonLocked(email, true);
		activateAccountTokenServiceImpl.delete(email);
		return true;
	}

	private String prepareText(String link){
		return "Hello Marcin!\r\n"
				+ "We received a request to create rtCommunicator account for you.\r\n"
				+ "Enter the following link to activate account: " + link;
	}
}
