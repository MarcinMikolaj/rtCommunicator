package project.rtc.authorization.oauth2;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import project.rtc.infrastructure.utils.CookieUtils;
import project.rtc.infrastructure.utils.jwt.JwtTokenProvider;

@Component
@RequiredArgsConstructor
public class OAuth2AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
	
	private final JwtTokenProvider jwtTokenProvider;

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		
		String jwtToken = jwtTokenProvider.createJwtToken(authentication, getEmailByAuthentication(authentication), null, null);
		CookieUtils.addCookie(response, "jwt", jwtToken, 1800000);
		response.addHeader("Authorization", "Bearer " + jwtToken);
			
		String targetUrl = "/app/panel";
		
		getRedirectStrategy().sendRedirect(request, response, targetUrl);
		
	}
	
	
	protected String determineTargetUrl(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication, String jwtToken) {
		
		String targetUrl = "/app/panel";
		
		String redirectUri = UriComponentsBuilder
				.fromUriString(targetUrl)
				.queryParam("jwt", jwtToken)
				.build()
				.toUriString();
		
		return redirectUri;
			
	}
	
	protected void clearAuthenticationAttributes(HttpServletRequest request, HttpServletResponse response) {
		
	}
	
	
	private String getEmailByAuthentication(Authentication authentication) {
		
		DefaultOAuth2User defaultOAuth2User = (DefaultOAuth2User) authentication.getPrincipal();
		
		if(defaultOAuth2User.getAttribute("email") == null) {
			throw new NullPointerException("CustomAuthenticationSuccessHandler.getEmailByAuthentication: no email attribute !");
		}
		
		String userEmail = (String) defaultOAuth2User.getAttribute("email");
		
		return userEmail;
		
	}

}
