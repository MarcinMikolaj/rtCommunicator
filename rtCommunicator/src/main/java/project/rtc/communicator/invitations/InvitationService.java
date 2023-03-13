package project.rtc.communicator.invitations;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import project.rtc.exceptions.NoAuthorizationTokenException;
import project.rtc.exceptions.UserNotFoundException;

public interface InvitationService {
	
	public Invitation create(String inviting, String invited);
	
	public List<Invitation> getAll(HttpServletRequest httpServletRequest) throws UserNotFoundException, NoAuthorizationTokenException;
	
	public List<InvitationResponsePayload> getInvitationPayload(HttpServletRequest httpServletRequest) throws UserNotFoundException, NoAuthorizationTokenException;
	
	// Enables the execution of method accept or decline depending on the decision made by the user on the invitation.
	// As an argument, it accepts payload (ReplyToInvitationRequest.class) received from the customer.
	public Invitation handleInvitation(ReplyToInvitationRequest replyToInvitationRequest);
	
	// Is responsible for accepting the invitation of a given user, which means removing the invitation and creating a room for users.
	// He accepts the invitation as an argument.
	public Invitation accept(Invitation invitation);
	
	// Responsible for removing the invitation and indicating it from the invited user after rejecting the invitation.
	// He accepts the invitation as an argument.
	public Invitation decline(Invitation invitation);

}
