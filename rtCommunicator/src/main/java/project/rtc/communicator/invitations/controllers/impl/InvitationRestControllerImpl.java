package project.rtc.communicator.invitations.controllers.impl;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import project.rtc.communicator.invitations.controllers.InvitationRestController;
import project.rtc.communicator.invitations.dto.InvitationRequestDto;
import project.rtc.communicator.invitations.dto.InvitationResponseDto;
import project.rtc.communicator.invitations.services.InvitationService;
import project.rtc.infrastructure.exception.exceptions.NoAuthorizationTokenException;
import project.rtc.infrastructure.exception.exceptions.RoomNotFoundException;
import project.rtc.infrastructure.exception.exceptions.UserNotFoundException;

@RestController
@RequiredArgsConstructor
public class InvitationRestControllerImpl implements InvitationRestController {
	
	private final InvitationService invitationService;

	@Override
	public ResponseEntity<List<InvitationResponseDto>> getInvitations(HttpServletRequest httpServletRequest)
			throws UserNotFoundException, NoAuthorizationTokenException {
		List<InvitationResponseDto> invitations = invitationService.getInvitations(httpServletRequest);
		return new ResponseEntity<>(invitations, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<?> acceptInvitation(InvitationRequestDto dto, HttpServletRequest httpServletRequest)
			throws UserNotFoundException, RoomNotFoundException, NoAuthorizationTokenException {
		return new ResponseEntity<>(invitationService.acceptInvitation(dto.getInvitationId(), httpServletRequest)
				, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<?> declineInvitation(InvitationRequestDto dto, HttpServletRequest httpServletRequest)
			throws UserNotFoundException, NoAuthorizationTokenException {
		return new ResponseEntity<>(invitationService.declineInvitation(dto.getInvitationId(), httpServletRequest)
				, HttpStatus.OK);
	}

}
