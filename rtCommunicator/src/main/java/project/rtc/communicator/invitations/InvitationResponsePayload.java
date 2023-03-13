package project.rtc.communicator.invitations;

import java.sql.Date;

import project.rtc.communicator.user.User;

public final class InvitationResponsePayload  {

	private final String identificator;
	private final String inviting;
	private final String invited;
	private final Date creation_date;
	private final User user;
	

	public InvitationResponsePayload(Invitation invitation, User user) {
		super();
		this.identificator = invitation.getIdentificator();
		this.invited = invitation.getInvited();
		this.inviting = invitation.getInviting();
		this.creation_date = invitation.getCreation_date();
		this.user = user;
	}

	
	public InvitationResponsePayload(String identificator, String inviting, String invited, Date creation_date,
			User user) {
		super();
		this.identificator = identificator;
		this.inviting = inviting;
		this.invited = invited;
		this.creation_date = creation_date;
		this.user = user;
	}

	
	public String getIdentificator() {
		return identificator;
	}
	

	public String getInviting() {
		return inviting;
	}
	
	
	public String getInvited() {
		return invited;
	}

	
	public Date getCreation_date() {
		return creation_date;
	}
	
	
	public User getUser() {
		return user;
	}
	

	@Override
	public String toString() {
		return "InvitationResponsePayload [identificator=" + identificator + ", inviting=" + inviting + ", invited="
				+ invited + ", creation_date=" + creation_date + ", user=" + user + "]";
	}
	
}
