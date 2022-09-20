package project.rtc.utils.jwt;

import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.springframework.security.core.Authentication;

import project.rtc.exceptions.NoAuthorizationTokenException;

public interface JwtTokenProvider {
	
	public String createJwtToken(Authentication authentication, String email, Date issuedAt,Date expitation);
	public String createJwtToken(String subject, Date issuedAt, Date expiration);
	public Date getTokentIssuedAt(String jwtToken);
	public Date getTokentExpiration(String jwtToken);
	public String getTokenSubject(String jwtToken);
	public boolean validateToken(String jwtToken);
	public String getJwtTokenFromCookie(HttpServletRequest request) throws NoAuthorizationTokenException;

}
