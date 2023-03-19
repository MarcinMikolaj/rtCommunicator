package project.rtc.communicator.invitations.dto;

import lombok.Data;

@Data
public class InvitationRequestPayload {
	
	// Indicates the nickname of the inviting person
	private String inviting;
	
	// Indicates the nickname of the invited person
	private String invited;
	
}
