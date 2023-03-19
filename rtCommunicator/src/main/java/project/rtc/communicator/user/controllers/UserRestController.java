package project.rtc.communicator.user.controllers;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import project.rtc.communicator.user.dto.User;
import project.rtc.communicator.user.dto.UserResponseBody;
import project.rtc.registration.dto.ProfilePicture;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@RequestMapping(value = "/app/account", produces = MediaType.APPLICATION_JSON_VALUE)
public interface UserRestController {

    // Responsible for sending information about the currently logged-in user
    @GetMapping(path = "/get")
    ResponseEntity<User> getActualLoggedUser(HttpServletRequest httpServletRequest);

    // Handles the request to change the user's nick
    @PostMapping(path = "/update/nick")
    ResponseEntity<UserResponseBody> updateUserNick(@RequestBody Map<String, ?> nick
            , HttpServletRequest httpServletRequest);

    // Handles the request to change the user's email
    @PostMapping(path = "/update/email")
    ResponseEntity<UserResponseBody> updateUserEmail(@RequestBody Map<String, ?> email
            , HttpServletRequest httpServletRequest);

    // Handles the request to change the user's password
    @PostMapping(path = "/update/password")
    ResponseEntity<UserResponseBody> updateUserPassword(@RequestBody Map<String, ?> attributes
            , HttpServletRequest httpServletRequest);

    // Handles the request to change the user's picture
    @PostMapping(path = "/update/picture")
    ResponseEntity<UserResponseBody> updateUserProfilePicture(@RequestBody ProfilePicture profilePicture
            , HttpServletRequest httpServletRequest);

    // Handles the request to delete user
    @PostMapping(path = "/delete")
    ResponseEntity<UserResponseBody> deleteUser(@RequestBody Map<String, ?> email, HttpServletRequest httpServletRequest
            , HttpServletResponse httpServletResponse);
}
