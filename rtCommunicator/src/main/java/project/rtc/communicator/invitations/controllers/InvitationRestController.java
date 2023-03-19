package project.rtc.communicator.invitations.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import project.rtc.communicator.invitations.dto.InvitationRequestPayload;
import project.rtc.communicator.invitations.dto.InvitationResponsePayload;
import project.rtc.communicator.invitations.dto.ReplyToInvitationRequest;
import project.rtc.exceptions.NoAuthorizationTokenException;
import project.rtc.exceptions.UserNotFoundException;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RequestMapping(value = "/app/rtc")
public interface InvitationRestController {

    // Is responsible for handling the query regarding the download of all invitations for the currently logged-in user.
    @GetMapping(path = "/invitation/get/all")
    ResponseEntity<List<InvitationResponsePayload>> getInvitations(HttpServletRequest httpServletRequest)
            throws UserNotFoundException, NoAuthorizationTokenException;


    // Responsible for creating an invitation for a given user.
    @PostMapping(path = "/room/invitation/send")
    ResponseEntity<String> sendInvitation(@RequestBody InvitationRequestPayload invitingRequestPayload
            , HttpServletRequest httpServletRequest);


    // Responsible for handling the decision on the given invitation.
    @PostMapping(path = "/invitation/decision")
    ResponseEntity<String> accept(@RequestBody ReplyToInvitationRequest replyToInvitationRequest);

}
