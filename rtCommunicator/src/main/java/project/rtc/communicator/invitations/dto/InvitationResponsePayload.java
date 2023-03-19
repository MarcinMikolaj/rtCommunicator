package project.rtc.communicator.invitations.dto;

import java.sql.Date;

import lombok.Getter;
import lombok.ToString;
import project.rtc.communicator.user.dto.User;

@Getter
@ToString
public final class InvitationResponsePayload  {

	private final String identificator;

	private final String inviting;

	private final String invited;

	private final Date creation_date;

	@ToString.Exclude
	private final User user;
	

	public InvitationResponsePayload(Invitation invitation, User user) {
		super();
		this.identificator = invitation.getIdentificator();
		this.invited = invitation.getInvited();
		this.inviting = invitation.getInviting();
		this.creation_date = invitation.getCreation_date();
		this.user = user;
	}

}
