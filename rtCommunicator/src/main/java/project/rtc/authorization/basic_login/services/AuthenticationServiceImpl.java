package project.rtc.authorization.basic_login.services;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import project.rtc.authorization.basic_login.controllers.dto.LoginRequestPayload;
import project.rtc.authorization.basic_login.controllers.dto.LoginResponsePayload;
import project.rtc.authorization.basic_login.controllers.dto.LogoutRequestPayload;
import project.rtc.authorization.basic_login.credentials.services.CredentialsService;
import project.rtc.utils.ConsoleColors;
import project.rtc.utils.CookieUtils;
import project.rtc.utils.jwt.JwtTokenProvider;

import java.io.IOException;

import javax.servlet.http.Cookie;

@Service
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

	private final AuthenticationManager authenticationManager;
	private final CredentialsService credentialsService;
	private final JwtTokenProvider jwtTokenProvider;

    //	Expire time for JWT token
	@Value("${app.security.jwt.expiry_time}")
	private Integer expiryTime;


	// This method authenticates the user trying to access the application.
	// If the user has an account, he will be assigned an authorization token under
	// which he will be able to access specific resources.
	@Override
	public LoginResponsePayload authenticate(HttpServletResponse response, LoginRequestPayload loginRequestPayload) {

		String authorizationToken;
		UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken;

		if (!credentialsService.exist(loginRequestPayload))
			return new LoginResponsePayload(null, false);

		usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(loginRequestPayload.getEmail(),
				loginRequestPayload.getPassword());
		Authentication authentication = authenticationManager.authenticate(usernamePasswordAuthenticationToken);

		authorizationToken = jwtTokenProvider.createJwtToken(authentication, loginRequestPayload.getEmail(), null,
				null);

		response.addHeader("Authorization", "Bearer " + authorizationToken);

		CookieUtils.addCookie(response, "jwt", authorizationToken,  expiryTime);

		return new LoginResponsePayload(null, true);

	}

	// This method allows the currently logged in user to log out. Clear current
	// SecurityContext.
	// The assigned authorization token is revoked.
	// Set current Authentication to false.
	@Override
	public void logout(HttpServletRequest request, HttpServletResponse response,
			LogoutRequestPayload logoutRequestPayload) throws IOException {

		CookieUtils.deleteCookie(request, response, "jwt");

		try {
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

			if (authentication != null) {
				if (!authentication.isAuthenticated())
					return;

				SecurityContextHolder.getContext().getAuthentication().setAuthenticated(false);
				SecurityContextHolder.clearContext();

			} else
				return;

		} catch (Exception e) {
			System.out.println(ConsoleColors.RED + "LoginServiceImpl.logout: User failed to logout, exception message:"
					+ e.getMessage() + ConsoleColors.RESET);
		}

		HttpSession session = request.getSession(false);

		session = request.getSession(false);

		if (session != null) {
			session.invalidate();
		}

		for (Cookie cookie : request.getCookies()) {
			cookie.setMaxAge(0);
		}

		response.sendRedirect("/app/login");

	}

	@Override
	public void logout(HttpServletRequest request, HttpServletResponse response) throws IOException {

		CookieUtils.deleteCookie(request, response, "jwt");

		try {
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

			if (authentication != null) {
				if (!authentication.isAuthenticated())
					return;

				SecurityContextHolder.getContext().getAuthentication().setAuthenticated(false);
				SecurityContextHolder.clearContext();

			} else
				return;

		} catch (Exception e) {
			System.out.println(ConsoleColors.RED + "LoginServiceImpl.logout: User failed to logout, exception message:"
					+ e.getMessage() + ConsoleColors.RESET);
		}

		HttpSession session = request.getSession(false);

		session = request.getSession(false);

		if (session != null) {
			session.invalidate();
		}

		for (Cookie cookie : request.getCookies()) {
			cookie.setMaxAge(0);
		}

		response.sendRedirect("/app/login");
	}
}
