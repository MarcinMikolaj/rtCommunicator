package project.rtc.infrastructure.utils.token;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import java.util.Date;
import java.util.HashMap;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import project.rtc.infrastructure.exception.exceptions.NoAuthorizationTokenException;

import io.jsonwebtoken.*;

//This class allows you to generate new JSON Web Token, get Token information like
//expire time or subject and validate the token
@Service
@Slf4j
public final class JwtTokenProvider {

	public static String create(String jwtSecretKey, String subject, Date issuedAt, Date expiration) {
		// Create token header
		Map<String, Object> headersForJwtToken = new HashMap<>();
		headersForJwtToken.put("alg", "HS512");
		headersForJwtToken.put("typ", "JWT");

		return Jwts.builder()
				.setHeader(headersForJwtToken)
				.setSubject(subject)
				.setIssuedAt(issuedAt)
				.setExpiration(expiration)
				.signWith(SignatureAlgorithm.HS512, jwtSecretKey)
		        .compact();
	}
	
	public static Date getTokenIssuedAt(String jwtSecretKey, String jwtToken) {
		return Jwts.parser().setSigningKey(jwtSecretKey).parseClaimsJws(jwtToken).getBody().getIssuedAt();
	}
	
    public static Date getTokenExpiration(String jwtSecretKey, String jwtToken) {
		return Jwts.parser().setSigningKey(jwtSecretKey).parseClaimsJws(jwtToken).getBody().getExpiration();
	}
	
	public static String getTokenSubject(String jwtSecretKey, String jwtToken) {
		return Jwts.parser().setSigningKey(jwtSecretKey).parseClaimsJws(jwtToken).getBody().getSubject();
	}

	// Checks if the provided token is valid (signature, expired, is supported, claims not empty).
	public static boolean validateToken(String jwtSecretKey, String jwtToken) {
		try {
			Jwts.parser().setSigningKey(jwtSecretKey).parseClaimsJws(jwtToken);
			return true;
		} catch (SignatureException ex) {
            log.error("JwtTokenProvider.validateToken: Invalid JWT signature !");
        } catch (MalformedJwtException ex) {
            log.error("JwtTokenProvider.validateToken: Invalid JWT token !");
        } catch (ExpiredJwtException ex) {
            log.error("JwtTokenProvider.validateToken: Expired JWT token !");
        } catch (UnsupportedJwtException ex) {
            log.error("JwtTokenProvider.validateToken: Unsupported JWT token !");
        } catch (IllegalArgumentException ex) {
            log.error("JwtTokenProvider.validateToken: JWT claims string is empty !");
        }
		return false;
	}
	
	// Return JSON Web Token from cookie as string using HttpServletRequest.
	// Returns null in the absence of cookies 'jwt' attribute.
	public static String getJwtTokenFromCookie(HttpServletRequest request) throws NoAuthorizationTokenException {
		Cookie[] cookies = request.getCookies();
		return Arrays.stream(cookies)
				 .filter(c -> c.getName().toString().equals("jwt"))
				 .map(Cookie::getValue)
				 .findFirst().orElseThrow(NoAuthorizationTokenException::new);
	}
		
}
