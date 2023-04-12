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
import project.rtc.infrastructure.exception.exceptions.AuthenticationException;
import project.rtc.authorization.basic_login.credentials.services.CredentialsService;
import project.rtc.authorization.basic_login.services.AuthenticationService;
import project.rtc.infrastructure.utils.CookieUtils;
import project.rtc.infrastructure.utils.token.JwtTokenProvider;

import java.io.IOException;
import java.util.Date;

import javax.servlet.http.Cookie;

@Service
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
	private final AuthenticationManager authenticationManager;
	private final CredentialsService credentialsService;

	@Value("${app.security.jwt.secret_key}")
	private String jwtSecretKey;

	@Value("${app.credentials.security.credentials-time-expire}")
	private long expiryTime;

	@Override
	public void authenticate(HttpServletResponse response, LoginRequestPayload loginRequestPayload) throws AuthenticationException {
		if (!credentialsService.exist(loginRequestPayload))
			throw new AuthenticationException();

		UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
				new UsernamePasswordAuthenticationToken(loginRequestPayload.getEmail(), loginRequestPayload.getPassword());
		authenticationManager.authenticate(usernamePasswordAuthenticationToken);

		String authorizationToken = JwtTokenProvider.create(jwtSecretKey
				, loginRequestPayload.getEmail()
				, new Date(System.currentTimeMillis())
				, new Date(System.currentTimeMillis() + expiryTime));
		response.addHeader("Authorization", "Bearer " + authorizationToken);
		CookieUtils.addCookie(response, "jwt", authorizationToken, (int) expiryTime);
	}

	@Override
	public void logout(HttpServletRequest request, HttpServletResponse response) throws IOException {
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
