package project.rtc.authorization.security.jwt;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.filter.OncePerRequestFilter;

// This class is used to read JWT authentication token from the request,
//verify it, and set Spring Security’s SecurityContext if the token is valid
public class JwtTokenAuthenticationFilter extends OncePerRequestFilter {
	
	private final String jwtSecretKey = "co3j@8cj^h33Su3nx927dns92mvheo@k*hd&h%ndh3946dhb2@8ck30^h2cbxHh2oSh%hsGjrHwoB83%6dhIdb%h3gd*83H63h";

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		String jwtToken = getJwtToken(request);
		
		
		request.setAttribute(jwtToken, jwtToken);
		filterChain.doFilter(request, response);
	}
	
	
	//Enables the correct fetching of the JWT token from the HttpServletRequest object
	private String getJwtToken(HttpServletRequest httpServletRequest) throws ServletException {
		
        String bearerToken = httpServletRequest.getHeader("Authorization");
    
        if(bearerToken == null || !bearerToken.startsWith("Bearer ")) {
    	   throw new ServletException("JwtTokenAuthenticationFilter.getJwtTokenBearer: Token is null or is incorrenct");
        } else {
    	   return bearerToken.substring(7,bearerToken.length());
        }
}

}
