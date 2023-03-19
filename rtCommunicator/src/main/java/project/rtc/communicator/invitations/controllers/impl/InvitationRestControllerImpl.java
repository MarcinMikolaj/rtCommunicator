package project.rtc.communicator.invitations.controllers.impl;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import project.rtc.communicator.invitations.controllers.InvitationRestController;
import project.rtc.communicator.invitations.dto.InvitationRequestPayload;
import project.rtc.communicator.invitations.dto.InvitationResponsePayload;
import project.rtc.communicator.invitations.dto.ReplyToInvitationRequest;
import project.rtc.communicator.invitations.services.InvitationService;
import project.rtc.exceptions.NoAuthorizationTokenException;
import project.rtc.exceptions.UserNotFoundException;

@RestController
@RequiredArgsConstructor
public class InvitationRestControllerImpl implements InvitationRestController {
	
	private final InvitationService invitationService;

	@Override
	public ResponseEntity<List<InvitationResponsePayload>> getInvitations(HttpServletRequest httpServletRequest)
			throws UserNotFoundException, NoAuthorizationTokenException {
		List<InvitationResponsePayload> invitations = invitationService.getInvitationPayload(httpServletRequest);
		return new ResponseEntity<>(invitations, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<String> sendInvitation(@RequestBody InvitationRequestPayload invitingRequestPayload
			, HttpServletRequest httpServletRequest) {
		invitationService.create(invitingRequestPayload.getInviting(), invitingRequestPayload.getInvited());
		return new ResponseEntity<>("ok", HttpStatus.OK);
	}

	@Override
	public ResponseEntity<String> accept(@RequestBody ReplyToInvitationRequest replyToInvitationRequest) {
		invitationService.handleInvitation(replyToInvitationRequest);
		return new ResponseEntity<>("decision", HttpStatus.OK);
	}
	
}
