package project.rtc.communicator.invitations.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import project.rtc.communicator.invitations.dto.InvitationRequestPayload;
import project.rtc.communicator.invitations.dto.InvitationResponsePayload;
import project.rtc.communicator.invitations.dto.ReplyToInvitationRequest;
import project.rtc.exceptions.NoAuthorizationTokenException;
import project.rtc.exceptions.UserNotFoundException;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface InvitationRestController {

    ResponseEntity<List<InvitationResponsePayload>> getInvitations(HttpServletRequest httpServletRequest) throws UserNotFoundException, NoAuthorizationTokenException;

    ResponseEntity<String> sendInvitation(@RequestBody InvitationRequestPayload invitingRequestPayload, HttpServletRequest httpServletRequest);

    ResponseEntity<String> accept(@RequestBody ReplyToInvitationRequest replyToInvitationRequest);
}
