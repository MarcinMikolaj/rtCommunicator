package project.rtc.authorization.security.jwt;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.nio.file.attribute.UserPrincipal;
import java.util.Date;
import java.util.HashMap;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.jsonwebtoken.*;

//This class allows you to generate new JSON Web Token, get Token information like
//expire time or subject and validate the token
@Service
public class JwtTokenProvider {
	
	private final String jwtSecretKey = "co3j@8cj^h33Su3nx927dns92mvheo@k*hd&h%ndh3946dhb2@8ck30^h2cbxHh2oSh%hsGjrHwoB83%6dhIdb%h3gd*83H63h";
	private final Long expireTime = (long) 1800000; //30min
	private static final Logger logger = LoggerFactory.getLogger(JwtTokenProvider.class);
	
    // Create JSON Web Token  
	public String createJwtToken(Authentication authentication) {
		
		UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
		
        // Create token header		
		Map<String, Object> headersForJwtToken = new HashMap<String, Object>();
		headersForJwtToken.put("typ", "JWT");
		headersForJwtToken.put("alg", "HS512");
		
		Long currentTimeInMili = System.currentTimeMillis();
		
		
		String token = Jwts.builder()
				.setHeader(headersForJwtToken)
				.setSubject(userPrincipal.getName())
				.setAudience("rtCommunicator")
				.setIssuedAt(new Date(currentTimeInMili))
				.setPayload("credentials-jwt")
				.setExpiration(new Date(currentTimeInMili + expireTime))
				.signWith(SignatureAlgorithm.HS512, jwtSecretKey)
		        .compact();
		
		return token;
		
	}
	
	//Return token expiration time
	public Date getTokenExpirationTime(String jwtToken) {
		return getTokenBody(jwtToken).getExpiration();
	}
	
	//Return token expiration time
	public String getTokenSubject(String jwtToken) {	
		return getTokenBody(jwtToken).getSubject();
	}
	
	private Claims getTokenBody(String jwtToken) {
		Claims claims = Jwts.parser()
				.setSigningKey(jwtSecretKey)
				.parseClaimsJws(jwtToken)
				.getBody();
		return claims;
	}
	
	public boolean validateToken(String jwtToken) {
		try {
			Jwts.parser().setSigningKey(jwtSecretKey).parseClaimsJws(jwtToken);
			return true;
		} catch (SignatureException ex) {
            logger.error("Invalid JWT signature");
        } catch (MalformedJwtException ex) {
            logger.error("Invalid JWT token");
        } catch (ExpiredJwtException ex) {
            logger.error("Expired JWT token");
        } catch (UnsupportedJwtException ex) {
            logger.error("Unsupported JWT token");
        } catch (IllegalArgumentException ex) {
            logger.error("JWT claims string is empty.");
        }
		
		return false;
	}
		
		

}
