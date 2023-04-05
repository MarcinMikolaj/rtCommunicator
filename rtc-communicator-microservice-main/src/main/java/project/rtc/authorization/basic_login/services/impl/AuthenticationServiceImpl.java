package project.rtc.authorization.basic_login.services.impl;

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
import project.rtc.authorization.basic_login.controllers.dto.LogoutRequestPayload;
import project.rtc.infrastructure.exception.exceptions.AuthenticationException;
import project.rtc.authorization.basic_login.credentials.services.CredentialsService;
import project.rtc.authorization.basic_login.services.AuthenticationService;
import project.rtc.infrastructure.utils.CookieUtils;
import project.rtc.infrastructure.utils.jwt.JwtTokenProvider;

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

	@Override
	public void authenticate(HttpServletResponse response, LoginRequestPayload loginRequestPayload) throws AuthenticationException {

		String authorizationToken;
		UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken;

		if (!credentialsService.exist(loginRequestPayload))
			throw new AuthenticationException();

		usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(loginRequestPayload.getEmail(),
				loginRequestPayload.getPassword());
		Authentication authentication = authenticationManager.authenticate(usernamePasswordAuthenticationToken);

		authorizationToken = jwtTokenProvider.createJwtToken(authentication, loginRequestPayload.getEmail(), null,
				null);

		response.addHeader("Authorization", "Bearer " + authorizationToken);
		CookieUtils.addCookie(response, "jwt", authorizationToken,  expiryTime);
	}

	@Override
	public void logout(HttpServletRequest request, HttpServletResponse response, LogoutRequestPayload logoutRequestPayload) throws IOException {

		CookieUtils.deleteCookie(request, response, "jwt");
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		if (authentication != null) {
			if (!authentication.isAuthenticated()){
				response.sendRedirect("/app/login");
				return;
			}
			SecurityContextHolder.getContext().getAuthentication().setAuthenticated(false);
			SecurityContextHolder.clearContext();
		} else
			return;

		HttpSession session = request.getSession(false);
		session = request.getSession(false);

		if (session != null)
			session.invalidate();

		for (Cookie cookie : request.getCookies())
			cookie.setMaxAge(0);

		response.sendRedirect("/app/login");
	}
}
