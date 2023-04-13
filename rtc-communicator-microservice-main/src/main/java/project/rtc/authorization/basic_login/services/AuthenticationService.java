package project.rtc.authorization.basic_login.services;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import project.rtc.authorization.basic_login.dto.LoginRequestPayload;
import project.rtc.infrastructure.exception.exceptions.AuthenticationException;

public interface AuthenticationService {

	// This method authenticates the user trying to access the application.
	// If the user has an account, he will be assigned an authorization token under which he will be able to access specific resources.
	void authenticate(HttpServletResponse response, LoginRequestPayload loginRequestPayload) throws AuthenticationException;

	// This method allows the currently logged-in user to log out. Clear current SecurityContext.
	// The assigned authorization token is revoked.
	// Set current Authentication to false.
	void logout(HttpServletRequest request, HttpServletResponse response) throws IOException;

}
