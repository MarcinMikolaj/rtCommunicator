package project.rtc.authorization.security;

import java.util.Base64;
import java.util.Optional;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.util.SerializationUtils;

public class CookieUtils {
	
	
	// Get cookie
	// name - cookie name
	public static Optional<Cookie> getCookie(HttpServletRequest request, String name){
		
		Cookie[] cookies = request.getCookies();
		
		if(cookies != null && cookies.length > 0) {
			for(Cookie cookie: cookies) {
				if(cookie.getName().equals(name)) {
					return Optional.of(cookie);
				}
			}
		}
		
		return Optional.empty();
	}
	
	// Allows you to add a cookie to the HTTP response
    // expireTime - This is maximum age in seconds age when the cookie will expire.
	// comment - Describe cookie purpose
	public static void addCookie(HttpServletResponse response, String name, String value, int expireTime, String comment) {
		
		Cookie cookie = new Cookie(name, value);
		
		cookie.setPath("/");
		cookie.setComment(comment);
		cookie.setMaxAge(expireTime);
		cookie.setHttpOnly(true);
		
		response.addCookie(cookie);
		
	}
	
	public static void deleteCookie(HttpServletRequest request, HttpServletResponse response, String name) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null && cookies.length > 0) {
            for (Cookie cookie: cookies) {
                if (cookie.getName().equals(name)) {
                    cookie.setValue("");
                    cookie.setPath("/");
                    cookie.setMaxAge(0);
                    response.addCookie(cookie);
                }
            }
        }
    }
	
	public static String serialize(Object object) {
		return Base64.getUrlEncoder().encodeToString(SerializationUtils.serialize(object));		
	}
	
	public static <T> T deserialize(Cookie cookie, Class<T> cls) {
		return cls.cast(SerializationUtils.deserialize(Base64.getUrlDecoder().decode(cookie.getValue())));
	}

}
