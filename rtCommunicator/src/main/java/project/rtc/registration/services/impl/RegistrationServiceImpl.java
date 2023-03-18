package project.rtc.registration.services.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import project.rtc.authorization.basic_login.credentials.services.CredentialsService;
import project.rtc.communicator.user.services.impl.UserServiceImpl;
import project.rtc.registration.activateAccountToken.dto.ActivateAccountToken;
import project.rtc.registration.activateAccountToken.services.impl.ActivateAccountTokenServiceImpl;
import project.rtc.registration.dto.ErrorMessage;
import project.rtc.registration.dto.ProfilePicture;
import project.rtc.registration.dto.RegistrationRequest;
import project.rtc.registration.dto.RegistrationResponse;
import project.rtc.registration.services.RegistrationService;
import project.rtc.utils.jwt.JwtTokenProvider;
import project.rtc.utils.mail.MailSenderService;

import javax.validation.Path;
import org.hibernate.validator.internal.engine.path.PathImpl;

@Service
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
@RequiredArgsConstructor
public class RegistrationServiceImpl implements RegistrationService {
	
	private final CredentialsService credentialsService;
    private final ActivateAccountTokenServiceImpl activateAccountTokenServiceImpl;
    private final MailSenderService mailSenderService;
	private final Validator validator;
	private final JwtTokenProvider jwtTokenProvider;
	private final UserServiceImpl userService;


	// User registration consists in creating new private credentials to which only the registrant has access
	// and a public user account sent to friends during e.g. refreshing the friends list in the customer panel
	public RegistrationResponse registerAccount(RegistrationRequest registerRequest) {

		RegistrationResponse registerResponse = valid(registerRequest);
		String accountCreateSuccessfulMessage = "The account has been created successfully";

		if(!registerResponse.isSuccessful())
			return registerResponse;


		credentialsService.createCredentialsAndSaveInDatabase(registerRequest);
		userService.createUserAndSaveInDataBase(registerRequest);

		sendActivateAccountLinkToEmail(registerRequest.getEmail());

		registerResponse.setSuccessfulMessage(accountCreateSuccessfulMessage);

		return registerResponse;
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
		
		String token;
		String email;
		ActivateAccountToken activateAccountToken;
		
		token = httpServletRequest.getParameter("token");
		
		try {
			activateAccountToken = activateAccountTokenServiceImpl.findByToken(token);
			email = jwtTokenProvider.getTokenSubject(token);
		} catch (Exception e) {
			System.out.print(e.getMessage());
			return false;
		}
		
		
		if(jwtTokenProvider.getTokentExpiration(token).before(new Date())) {
			return false;
		}
			
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

	// If the user provided correct data in the query, an instance of the "RegistrationResponse" class
	// is returned with the isSuccessful parameter set to true, otherwise false.
    // The class instance is also loaded with error information.
	private RegistrationResponse valid(RegistrationRequest reqisterRequest) {
		
		RegistrationResponse registerResponse = new RegistrationResponse();
		List<ErrorMessage> errorMessages = new ArrayList<ErrorMessage>();
		
		registerResponse.setErrorMessages(errorMessages);
		registerResponse.setSuccessful(true);
			
	    // Go through the filters and log errors in the class RegistrationResponse
		registerResponse = validRegistrationRequest(reqisterRequest, registerResponse, errorMessages);	
		registerResponse = validProfilePicture(reqisterRequest, registerResponse, errorMessages);
        
		return registerResponse;
		
	}

	// This method allows you to validate for an instance of class RegistrationResponse and write all errors to the instance of the class RegistrationResponse
	private RegistrationResponse validRegistrationRequest(RegistrationRequest registerRequest
			, RegistrationResponse registerResponse, List<ErrorMessage> errorMessages) {
		
		Set<ConstraintViolation<RegistrationRequest>> errors = validator.validate(registerRequest);
		
		if(!errors.isEmpty()) {
			errors.forEach(err -> { 
				System.out.println("validation for RegistrationRequest instance: " + err);
				ErrorMessage errorMessage = new ErrorMessage(getFieldFromPaths(err.getPropertyPath()), err.getMessage());
				errorMessages.add(errorMessage);
			});		
			registerResponse.setSuccessful(false);
        } 
		
		return registerResponse;
	}

	// This method allows you to validate for an instance of class ProfilePicture and write all errors to the instance of the class RegistrationResponse
	private RegistrationResponse validProfilePicture(RegistrationRequest registerRequest
			, RegistrationResponse registerResponse, List<ErrorMessage> errorMessages) {
		
		
		Set<ConstraintViolation<ProfilePicture>> errors = validator.validate(registerRequest.getPicture());
		
		 if(!errors.isEmpty()) {
			 errors.forEach(err -> { 
				    System.out.println("validation for RegistrationRequest instance: " + err);
					ErrorMessage errorMessage = new ErrorMessage("picture." + getFieldFromPaths(err.getPropertyPath())
							, err.getMessage());
					errorMessages.add(errorMessage);
				});	
	        	registerResponse.setSuccessful(false);
	        }
		 
		 return registerResponse;
	}

	private String getFieldFromPaths(Path fieldPath) {
		PathImpl pathImpl = (PathImpl) fieldPath;
		return pathImpl.getLeafNode().toString();
	}
	
}
