package project.rtc.communicator.invitations.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.rtc.infrastructure.exception.exceptions.InvitationNotFoundException;
import project.rtc.infrastructure.exception.exceptions.NoAuthorizationTokenException;
import project.rtc.infrastructure.exception.exceptions.RoomNotFoundException;
import project.rtc.infrastructure.exception.exceptions.UserNotFoundException;

import javax.servlet.http.HttpServletRequest;

@RequestMapping(value = "/app/api/invitation")
public interface InvitationRestController {

    @GetMapping(path = "/get/all")
    ResponseEntity<?> getInvitations(HttpServletRequest httpServletRequest)
            throws UserNotFoundException, NoAuthorizationTokenException, InvitationNotFoundException;

    @PostMapping(value = "/accept")
    ResponseEntity<?> acceptInvitation(@RequestParam String invitationId
            , HttpServletRequest httpServletRequest) throws UserNotFoundException
            , RoomNotFoundException, NoAuthorizationTokenException, InvitationNotFoundException;

    @PostMapping(value = "/decline")
    ResponseEntity<?> declineInvitation(@RequestParam String invitationId
            , HttpServletRequest httpServletRequest) throws UserNotFoundException
            , NoAuthorizationTokenException, InvitationNotFoundException;

}
