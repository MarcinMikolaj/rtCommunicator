package project.rtc.infrastructure.utils;

import java.util.Base64;
import java.util.Optional;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.util.SerializationUtils;

public final class CookieUtils {
	
	// Allows you to add a cookie to the HTTP response.
    // expireTime - This is maximum age in seconds age when the cookie will expire.
	public static Cookie addCookie(HttpServletResponse response, String name, String value, int expireTime) {
		Cookie cookie = new Cookie(name, value);
		cookie.setPath("/");
		cookie.setMaxAge(expireTime);
		cookie.setHttpOnly(true);
		response.addCookie(cookie);
		return cookie;
	}

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
	
	// Remove Cookies from request
	public static void deleteCookie(HttpServletRequest request, HttpServletResponse response, String name) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null && cookies.length > 0) {
            for (Cookie cookie: cookies) {
                if (cookie.getName().equals(name)) {
                    cookie.setValue("");
                    cookie.setPath("/");
                    cookie.setMaxAge(0);
                    cookie.setSecure(true);
                    cookie.setHttpOnly(true);
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
