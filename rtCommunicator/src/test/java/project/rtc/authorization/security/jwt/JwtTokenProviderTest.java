package project.rtc.authorization.security.jwt;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Collections;
import java.util.Date;
import org.junit.jupiter.api.Test;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.User;

import project.rtc.utils.jwt.JwtTokenProvider;
import project.rtc.utils.jwt.JwtTokenProviderImpl;

class JwtTokenProviderTest {
	
	@Test
	void createdTokenShouldBeTheSameAsTheSampleOne() {
		
		//given
		JwtTokenProvider jwtTokenProvider = new JwtTokenProviderImpl();
		User user = new User("username", "password", Collections.emptyList());
		UsernamePasswordAuthenticationToken userAuthentication = 
				new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
		Date issuedAt = new Date(1661100000);
		Date expirationTime = new Date(1661100000 + 3600000); //1,664,700,000
		
		
		//when
		String token = jwtTokenProvider.createJwtToken(userAuthentication,
				"ewelina23@gmail.com", issuedAt, expirationTime);
		
		//then
		assertEquals("eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJld2VsaW5hMjNAZ21haWwuY29tIiwiaWF0IjoxNjYxMTAwLCJleHAiOjE2NjQ3MDB9.kSxdp1EJrZLx4yvAyNRIjgRGDD4_NmYCwDOvFgTN_ejYskAM9SvAwdyzsBe2YjH-gNi2VyyvxvaNX35tM1_zww",
				token);
	}

}
