package project.rtc.registration.services.impl;

import java.util.Date;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import org.springframework.web.bind.MethodArgumentNotValidException;
import project.rtc.authorization.basic_login.credentials.services.CredentialsService;
import project.rtc.communicator.user.services.impl.UserServiceImpl;
import project.rtc.registration.activateAccountToken.entities.ActivateAccountToken;
import project.rtc.registration.activateAccountToken.services.impl.ActivateAccountTokenServiceImpl;
import project.rtc.registration.dto.RegistrationRequestDto;
import project.rtc.registration.services.RegistrationService;
import project.rtc.infrastructure.utils.jwt.JwtTokenProvider;
import project.rtc.infrastructure.utils.mail.MailSenderService;

@Service
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
@RequiredArgsConstructor
public class RegistrationServiceImpl implements RegistrationService {
	
	private final CredentialsService credentialsService;
    private final ActivateAccountTokenServiceImpl activateAccountTokenServiceImpl;
    private final MailSenderService mailSenderService;
	private final JwtTokenProvider jwtTokenProvider;
	private final UserServiceImpl userService;

	// User registration consists in creating new private credentials to which only the registrant has access
	// and a public user account sent to friends during e.g. refreshing the friends list in the customer panel
	public void registerAccount(RegistrationRequestDto dto) throws MethodArgumentNotValidException {
		credentialsService.createCredentialsAndSaveInDatabase(dto);
		userService.create(dto.getNick(), dto.getEmail(), dto.getPicture());
		// TODO: Uncomment after tests.
		//sendActivateAccountLinkToEmail(registrationRequest.getEmail());
	}
	
	private boolean sendActivateAccountLinkToEmail(String email) {
		
		ActivateAccountToken activateAccountToken;
		String link;
		
		activateAccountToken = activateAccountTokenServiceImpl.assignNewTokenToAccount(email);

		link = "http://localhost:8080/app/registration/activate" + "?token=" + activateAccountToken.getToken();
		
		String text = "Hello Marcin!\r\n"
				+ "We received a request to create rtCommunicator account for you.\r\n"
				+ "Enter the following link to activate account: " + link;
		
		try {
			mailSenderService.sendMessage(email, "Acitave Link", text);
			return true;
		} catch (MessagingException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	// Enables the account to be activated if a valid token has been provided by the user
	public boolean activateAccount(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {

		String email;
		ActivateAccountToken activateAccountToken;

		String token = httpServletRequest.getParameter("token");
		
		try {
			activateAccountToken = activateAccountTokenServiceImpl.findByToken(token);
			email = jwtTokenProvider.getTokenSubject(token);
		} catch (Exception e) {
			System.out.print(e.getMessage());
			return false;
		}

		if(jwtTokenProvider.getTokentExpiration(token).before(new Date()))
			return false;

			
		if(activateAccountToken.getToken().equals(token)) {
			credentialsService.setAccountNonLocked(email, true);
			activateAccountTokenServiceImpl.delete(email);
			try {	
				httpServletResponse.sendRedirect(email);	
			} catch (Exception e) {
				System.out.print(e.getMessage());
			}
			return true;
		}
		
		return false;
	}
	
	public void activateAccount(String email) {
		credentialsService.setAccountNonLocked(email, true);
	}

}
