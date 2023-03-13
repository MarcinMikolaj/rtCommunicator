package project.rtc.communicator.invitations;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
// Template for the decision about the invitation received from the user.
public class ReplyToInvitationRequest {
	
	// Indicates whether the friend request has been accepted.
	private boolean accepted;
	
	// Indicates to invitation identificator.
	private String identyficator;
	
	private String roomId;
	
}
