package project.rtc.authorization.security.filters;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import project.rtc.authorization.security.CustomUserDetailsService;
import project.rtc.infrastructure.utils.ConsoleColors;
import project.rtc.infrastructure.utils.CookieUtils;
import project.rtc.infrastructure.utils.token.JwtTokenProvider;

// This class is used to read JWT authentication token from the request, verify it, and set Spring Security’s SecurityContext if the token is valid.
@Component
@RequiredArgsConstructor
public class JwtTokenAuthenticationFilter extends OncePerRequestFilter {

    private final CustomUserDetailsService customUserDetailsService;
	@Value("${app.security.jwt.secret_key}")
	private String jwtSecretKey;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		try {	
			String jwtToken = getJwtTokenFromCookie(request);
			if(JwtTokenProvider.validateToken(jwtSecretKey, jwtToken)) {
				String username = JwtTokenProvider.getTokenSubject(jwtSecretKey, jwtToken);
				UserDetails userDetails = customUserDetailsService.loadUserByUsername(username);
				
				UsernamePasswordAuthenticationToken userAuthentication = 
						new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
				
				userAuthentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				
				// Set user identity on the Spring Security context
				SecurityContextHolder.getContext().setAuthentication(userAuthentication);
			}
			else
				logout(request, response);

		} catch (Exception e) {
			logout(request, response);
		}
		filterChain.doFilter(request, response);
	}

	// - Set current Authentication to false,
	// - Clear current SecurityContext, 
	// - Remove JSON Web Token assigned to the user
	public void logout(HttpServletRequest request, HttpServletResponse respone) {
		CookieUtils.deleteCookie(request, respone, "jwt");
		try {
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			if(authentication != null) {
				Boolean isAuthenticated = authentication.isAuthenticated();
				
				if(isAuthenticated == false) 
					return;

				SecurityContextHolder.getContext().getAuthentication().setAuthenticated(false);
				SecurityContextHolder.clearContext();
				
			}
		} catch (Exception e) {
			System.out.println(ConsoleColors.RED
					+ "JwtTokenAuthenticationFilter.logout: User failed to logout, exception message:"
					+ e.getMessage() + ConsoleColors.RESET);	
		}
	}

	// Return JSON Web Token from header as string using HttpServletRequest
	private String getJwtToken(HttpServletRequest httpServletRequest) throws ServletException {
        String bearerToken = httpServletRequest.getHeader("Authorization");
        if(bearerToken == null || !bearerToken.startsWith("Bearer "))
			throw new ServletException("JwtTokenAuthenticationFilter.getJwtTokenBearer: Token is null or is incorrect");
         else
    	   return bearerToken.substring(7,bearerToken.length());

}
	
    // Return JSON Web Token from cookie as string using HttpServletRequest
	private String getJwtTokenFromCookie(HttpServletRequest request) throws ServletException {
		Cookie[] cookies = request.getCookies();
		String jwtToken = null;
		
		for(Cookie cookie: cookies) {
			if(cookie.getName().toString().equals("jwt")) {
				jwtToken = cookie.getValue().toString();
				return jwtToken;
			}
		}
		 throw new ServletException(ConsoleColors.CYAN_BACKGROUND +
				 "JwtTokenAuthenticationFilter.getJwtTokenFromCooki: Token is null or is incorrect"
				 + ConsoleColors.RESET);
	}

}
