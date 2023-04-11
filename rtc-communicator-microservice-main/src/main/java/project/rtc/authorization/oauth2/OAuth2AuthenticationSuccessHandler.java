package project.rtc.authorization.oauth2;

import java.io.IOException;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import project.rtc.infrastructure.utils.CookieUtils;
import project.rtc.infrastructure.utils.token.JwtTokenProvider;

@Component
@RequiredArgsConstructor
public class OAuth2AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

	@Value("${app.security.jwt.secret_key}")
	private String jwtSecretKey;

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException {

		String jwtToken = JwtTokenProvider.create(jwtSecretKey
				,getEmailByAuthentication(authentication)
				, new Date(System.currentTimeMillis())
				, new Date(System.currentTimeMillis() + (long) 1800000));

		CookieUtils.addCookie(response, "jwt", jwtToken, 1800000);
		response.addHeader("Authorization", "Bearer " + jwtToken);
		String targetUrl = "/app/panel";
		getRedirectStrategy().sendRedirect(request, response, targetUrl);
	}
	
	
	protected String determineTargetUrl(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication, String jwtToken) {
		String targetUrl = "/app/panel";
		return UriComponentsBuilder
				.fromUriString(targetUrl)
				.queryParam("jwt", jwtToken)
				.build()
				.toUriString();
	}

	private String getEmailByAuthentication(Authentication authentication) {
		DefaultOAuth2User defaultOAuth2User = (DefaultOAuth2User) authentication.getPrincipal();
		if(defaultOAuth2User.getAttribute("email") == null)
			throw new NullPointerException("CustomAuthenticationSuccessHandler.getEmailByAuthentication: no email attribute !");
		return defaultOAuth2User.getAttribute("email");
	}

}
