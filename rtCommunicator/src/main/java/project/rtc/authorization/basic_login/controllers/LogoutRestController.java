package project.rtc.authorization.basic_login.controllers;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import project.rtc.authorization.basic_login.controllers.pojo.LogoutRequest;
import project.rtc.authorization.security.jwt.JwtTokenAuthenticationFilter;

@RestController
public class LogoutRestController {
	
	private JwtTokenAuthenticationFilter jwtTokenAuthenticationFilter;
	
	@Autowired
	public void setJwtTokenAuthenticationFilter(JwtTokenAuthenticationFilter jwtTokenAuthenticationFilter) {
		this.jwtTokenAuthenticationFilter = jwtTokenAuthenticationFilter;
	}
	
	// Handles user logout
	@RequestMapping(path = "/app/logout", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> logout(@RequestBody LogoutRequest logoutRequest, HttpServletRequest request, HttpServletResponse response){
			
		jwtTokenAuthenticationFilter.logout(request, response);
		SecurityContextHolder.clearContext();
		
		HttpSession session= request.getSession(false);
		session= request.getSession(false);
        if(session != null) {
            session.invalidate();
        }
        for(Cookie cookie : request.getCookies()) {
            cookie.setMaxAge(0);
        }

		return ResponseEntity.ok("logged out");
	}

}
