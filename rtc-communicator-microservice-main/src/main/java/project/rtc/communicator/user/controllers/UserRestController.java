package project.rtc.communicator.user.controllers;

import project.rtc.infrastructure.validators.user.ProfilePictureExtension;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import project.rtc.infrastructure.exception.exceptions.NoAuthorizationTokenException;
import project.rtc.infrastructure.exception.exceptions.UserNotFoundException;
import project.rtc.infrastructure.validators.user.ExistsByEmail;
import project.rtc.infrastructure.validators.user.ExistsByNick;
import project.rtc.infrastructure.validators.user.Nick;
import project.rtc.infrastructure.validators.user.Password;
import project.rtc.registration.dto.ProfilePicture;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@RequestMapping(value = "/app/api/user", produces = MediaType.APPLICATION_JSON_VALUE)
@Validated
public interface UserRestController {

    @GetMapping(value = "/get")
    ResponseEntity<?> getActualLoggedUser(HttpServletRequest httpServletRequest)
            throws UserNotFoundException, NoAuthorizationTokenException;

    @PostMapping(value = "/update/nick")
    ResponseEntity<?> updateUserNick(@RequestParam @Nick @ExistsByNick String nick
            , HttpServletRequest httpServletRequest ) throws UserNotFoundException, NoAuthorizationTokenException
            , MethodArgumentNotValidException;

    @PostMapping(value = "/update/email")
    ResponseEntity<?> updateUserEmail(@RequestParam @NotBlank @Email @ExistsByEmail String email
            , HttpServletRequest httpServletRequest) throws UserNotFoundException, NoAuthorizationTokenException
            , MethodArgumentNotValidException;

    @PostMapping(value = "/update/password")
    ResponseEntity<?> updateUserPassword(@RequestParam @Password String password
            , HttpServletRequest httpServletRequest) throws UserNotFoundException, NoAuthorizationTokenException
            , MethodArgumentNotValidException;

    @PostMapping(value = "/update/picture")
    ResponseEntity<?> updateUserProfilePicture(@RequestBody @ProfilePictureExtension ProfilePicture profilePicture
            , HttpServletRequest httpServletRequest) throws UserNotFoundException, NoAuthorizationTokenException
            , MethodArgumentNotValidException;

    @PostMapping(value = "/delete")
    ResponseEntity<?> deleteUser(@RequestParam @ExistsByEmail String email
            , HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse)
            throws UserNotFoundException, NoAuthorizationTokenException, MethodArgumentNotValidException;
}
