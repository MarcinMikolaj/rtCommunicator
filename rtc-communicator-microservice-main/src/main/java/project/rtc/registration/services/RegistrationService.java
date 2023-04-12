package project.rtc.registration.services;

import project.rtc.communicator.user.entities.User;
import project.rtc.infrastructure.exception.exceptions.InvalidTokenException;
import project.rtc.registration.dto.RegistrationRequestDto;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface RegistrationService {

    // User registration consists in creating new private credentials to which only the registrant has access
    // and a public user account sent to friends during e.g. refreshing the friends list in the customer panel.
    User registerAccount(RegistrationRequestDto dto) throws MessagingException;

    // Enables the account to be activated if a valid token has been provided by the user
    boolean activateAccount(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse)
            throws InvalidTokenException;
}
