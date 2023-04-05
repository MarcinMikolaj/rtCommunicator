package project.rtc.communicator.invitations.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.rtc.communicator.invitations.dto.InvitationRequestDto;
import project.rtc.infrastructure.exception.exceptions.NoAuthorizationTokenException;
import project.rtc.infrastructure.exception.exceptions.RoomNotFoundException;
import project.rtc.infrastructure.exception.exceptions.UserNotFoundException;

import javax.servlet.http.HttpServletRequest;

@RequestMapping(value = "/app/api")
public interface InvitationRestController {

    @GetMapping(path = "/invitation/get/all")
    ResponseEntity<?> getInvitations(HttpServletRequest httpServletRequest)
            throws UserNotFoundException, NoAuthorizationTokenException;

    @PostMapping(value = "/invitation/accept")
    ResponseEntity<?> acceptInvitation(@RequestBody InvitationRequestDto dto
            , HttpServletRequest httpServletRequest) throws UserNotFoundException
            , RoomNotFoundException, NoAuthorizationTokenException;

    @PostMapping(value = "/invitation/decline")
    ResponseEntity<?> declineInvitation(@RequestBody InvitationRequestDto dto
            , HttpServletRequest httpServletRequest) throws UserNotFoundException, NoAuthorizationTokenException;

}
