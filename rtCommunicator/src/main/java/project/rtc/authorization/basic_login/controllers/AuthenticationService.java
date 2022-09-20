package project.rtc.authorization.basic_login.controllers;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import project.rtc.authorization.basic_login.controllers.pojo.LoginRequestPayload;
import project.rtc.authorization.basic_login.controllers.pojo.LoginResponsePayload;
import project.rtc.authorization.basic_login.controllers.pojo.LogoutRequestPayload;

public interface AuthenticationService {
	
	// This method authenticates the user trying to access the application.
	// If the user has an account, he will be assigned an authorization token under which he will be able to access specific resources.
	public LoginResponsePayload authenticate(HttpServletResponse response, LoginRequestPayload loginRequestPayload);
	
	
	// This method allows the currently logged in user to log out. Clear current SecurityContext.
	// The assigned authorization token is revoked.
	// Set current Authentication to false.
	public void logout(HttpServletRequest request, HttpServletResponse response, LogoutRequestPayload logoutRequestPayload) throws IOException;

}
