package project.rtc.communicator.invitations;

public class InvitationRequestPayload {
	
	// Indicates the nickname of the inviting person
	private String inviting;
	
	// Indicates the nickname of the invited person
	private String invited;

	
	public InvitationRequestPayload() {
		super();
	}

	public InvitationRequestPayload(String inviting, String invited) {
		super();
		this.inviting = inviting;
		this.invited = invited;
	}

	public String getInviting() {
		return inviting;
	}

	public void setInviting(String inviting) {
		this.inviting = inviting;
	}

	public String getInvited() {
		return invited;
	}

	public void setInvited(String invited) {
		this.invited = invited;
	}

	@Override
	public String toString() {
		return "InvitingRequestPayload [inviting=" + inviting + ", invited=" + invited + "]";
	}
	
}
