package project.rtc.communicator.invitations.controllers.impl;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import project.rtc.communicator.invitations.controllers.InvitationRestController;
import project.rtc.communicator.invitations.dto.InvitationRequestPayload;
import project.rtc.communicator.invitations.dto.InvitationResponsePayload;
import project.rtc.communicator.invitations.dto.ReplyToInvitationRequest;
import project.rtc.communicator.invitations.services.InvitationService;
import project.rtc.communicator.invitations.services.InvitationServiceImpl;
import project.rtc.exceptions.NoAuthorizationTokenException;
import project.rtc.exceptions.UserNotFoundException;

@RestController
@RequiredArgsConstructor
public class InvitationRestControllerImpl implements InvitationRestController {
	
	private final InvitationService invitationService;
	

	// Is responsible for handling the query regarding the download of all invitations for the currently logged in user.
	@Override
	@RequestMapping(path = "/app/rtc/invitation/get/all", method = RequestMethod.GET)
	public ResponseEntity<List<InvitationResponsePayload>> getInvitations(HttpServletRequest httpServletRequest) throws UserNotFoundException, NoAuthorizationTokenException {
		
		List<InvitationResponsePayload> invitations = invitationService.getInvitationPayload(httpServletRequest);
		
		return new ResponseEntity<List<InvitationResponsePayload>>(invitations, HttpStatus.OK);
	}
	
	
	// Responsible for creating an invitation for a given user.
	@Override
	@RequestMapping(path = "/app/rtc/room/invitation/send", method = RequestMethod.POST)
	public ResponseEntity<String> sendInvitation(@RequestBody InvitationRequestPayload invitingRequestPayload, HttpServletRequest httpServletRequest) {
		
		invitationService.create(invitingRequestPayload.getInviting(), invitingRequestPayload.getInvited());
		System.out.println("Invitation request received, payload: " + invitingRequestPayload);
		
		return new ResponseEntity<String>("ok", HttpStatus.OK);
	}
	
	
	// Responsible for handling the decision on the given invitation.
	@Override
	@RequestMapping(path = "/app/rtc/invitation/decision", method = RequestMethod.POST)
	public ResponseEntity<String> accept(@RequestBody ReplyToInvitationRequest replyToInvitationRequest) {
		
		invitationService.handleInvitation(replyToInvitationRequest);
		System.out.println(replyToInvitationRequest.getIdentyficator());
		
		return new ResponseEntity<String>("decision", HttpStatus.OK);
	}
	
}
