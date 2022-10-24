package project.rtc.communicator.invitations;

// Template for the decision about the invitation received from the user.
public class ReplyToInvitationRequest {
	
	// Indicates whether the friend request has been accepted.
	private boolean accepted;
	
	// Indicates to invitation identificator.
	private String identyficator;
	
	private String roomId;
	
	
	public ReplyToInvitationRequest() {
		super();
	}

	public ReplyToInvitationRequest(boolean accepted, String identyficator) {
		super();
		this.accepted = accepted;
		this.identyficator = identyficator;
	}

	
	public boolean isAccepted() {
		return accepted;
	}

	public void setAccepted(boolean accepted) {
		this.accepted = accepted;
	}

	public String getIdentyficator() {
		return identyficator;
	}

	public void setIdentyficator(String identyficator) {
		this.identyficator = identyficator;
	}


	@Override
	public String toString() {
		return "ReplyToInvitationRequest [accepted=" + accepted + ", identyficator=" + identyficator + "]";
	}
	
}
