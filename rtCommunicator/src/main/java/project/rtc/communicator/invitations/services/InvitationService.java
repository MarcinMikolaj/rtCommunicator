package project.rtc.communicator.invitations.services;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import project.rtc.communicator.invitations.entities.Invitation;
import project.rtc.communicator.invitations.dto.InvitationResponseDto;
import project.rtc.infrastructure.exception.exceptions.NoAuthorizationTokenException;
import project.rtc.infrastructure.exception.exceptions.RoomNotFoundException;
import project.rtc.infrastructure.exception.exceptions.UserNotFoundException;

public interface InvitationService {

	Invitation create(String roomId, String invited, String inviting) throws UserNotFoundException;

	List<InvitationResponseDto> getInvitations(HttpServletRequest httpServletRequest)
			throws UserNotFoundException, NoAuthorizationTokenException;

	// Is responsible for accepting the invitation of a given user, which means removing the invitation and creating a room for users.
	// He accepts the invitation as an argument.
	List<InvitationResponseDto> acceptInvitation(String invitationId, HttpServletRequest httpServletRequest) throws UserNotFoundException, RoomNotFoundException, NoAuthorizationTokenException;
	
	// Responsible for removing the invitation and indicating it from the invited user after rejecting the invitation.
	// He accepts the invitation as an argument.
	List<InvitationResponseDto> declineInvitation(String invitationId, HttpServletRequest httpServletRequest) throws UserNotFoundException, NoAuthorizationTokenException;

}
