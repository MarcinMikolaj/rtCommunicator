package project.rtc.communicator.user.controllers;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import project.rtc.communicator.user.dto.UserRequestBody;
import project.rtc.infrastructure.exception.exceptions.NoAuthorizationTokenException;
import project.rtc.infrastructure.exception.exceptions.UserNotFoundException;
import project.rtc.infrastructure.groups.user.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RequestMapping(value = "/app/account", produces = MediaType.APPLICATION_JSON_VALUE)
public interface UserRestController {

    @GetMapping(value = "/get")
    ResponseEntity<?> getActualLoggedUser(HttpServletRequest httpServletRequest)
            throws UserNotFoundException, NoAuthorizationTokenException;

    @PostMapping(value = "/update/nick")
    ResponseEntity<?> updateUserNick(@RequestBody @Validated(UpdateUserNickGroup.class) UserRequestBody body
            , HttpServletRequest httpServletRequest ) throws UserNotFoundException, NoAuthorizationTokenException
            , MethodArgumentNotValidException;

    @PostMapping(value = "/update/email")
    ResponseEntity<?> updateUserEmail(@RequestBody @Validated(UpdateUserEmailGroups.class) UserRequestBody body
            , HttpServletRequest httpServletRequest) throws UserNotFoundException, NoAuthorizationTokenException
            , MethodArgumentNotValidException;

    @PostMapping(value = "/update/password")
    ResponseEntity<?> updateUserPassword(@RequestBody @Validated(UpdateUserPasswordGroup.class) UserRequestBody body
            , HttpServletRequest httpServletRequest) throws UserNotFoundException, NoAuthorizationTokenException
            , MethodArgumentNotValidException;

    @PostMapping(value = "/update/picture")
    ResponseEntity<?> updateUserProfilePicture(@RequestBody @Validated(UpdateUserPictureGroup.class) UserRequestBody body
            , HttpServletRequest httpServletRequest) throws UserNotFoundException, NoAuthorizationTokenException
            , MethodArgumentNotValidException;

    @PostMapping(value = "/delete")
    ResponseEntity<?> deleteUser(@RequestBody @Validated(DeleteUserGroup.class) UserRequestBody body
            , HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws UserNotFoundException
            , NoAuthorizationTokenException, MethodArgumentNotValidException;
}
