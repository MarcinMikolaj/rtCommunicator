package project.rtc.communicator.invitations.dto;

import lombok.Data;

// Template for the decision about the invitation received from the user.
@Data
public class ReplyToInvitationRequest {
	
	// Indicates whether the friend request has been accepted.
	private boolean accepted;
	
	// Indicates to invitation identificator.
	private String identyficator;
	
	private String roomId;
	
}
