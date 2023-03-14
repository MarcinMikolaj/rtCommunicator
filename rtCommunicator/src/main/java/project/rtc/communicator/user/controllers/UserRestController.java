package project.rtc.communicator.user.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import project.rtc.communicator.user.dto.User;
import project.rtc.communicator.user.dto.UserResponseBody;
import project.rtc.registration.dto.ProfilePicture;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

public interface UserRestController {

    ResponseEntity<User> getActualLoggedUser(HttpServletRequest httpServletRequest);

    ResponseEntity<UserResponseBody> updateUserNick(@RequestBody Map<String, ?> nick, HttpServletRequest httpServletRequest);

    ResponseEntity<UserResponseBody> updateUserEmail(@RequestBody Map<String, ?> email, HttpServletRequest httpServletRequest);

    ResponseEntity<UserResponseBody> updateUserPassword(@RequestBody Map<String, ?> attributes, HttpServletRequest httpServletRequest);

    ResponseEntity<UserResponseBody> updateUserProfilePicture(@RequestBody ProfilePicture profilePicture, HttpServletRequest httpServletRequest);

    ResponseEntity<UserResponseBody> deleteUser(@RequestBody Map<String, ?> email, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse);
}
