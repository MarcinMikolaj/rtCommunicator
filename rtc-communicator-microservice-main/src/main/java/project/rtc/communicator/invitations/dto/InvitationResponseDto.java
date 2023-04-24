package project.rtc.communicator.invitations.dto;

import java.util.Date;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import project.rtc.communicator.invitations.entities.InvitationOperation;
import project.rtc.communicator.user.entities.User;

@Getter
@ToString
@Builder
public final class InvitationResponseDto {
	public final Date timestamp;
	public final InvitationOperation operation;
	public final String invitationId;
	public final String roomName;
	public final String inviting;
	public final String invited;
	public final String creation_date;
	@ToString.Exclude
	private final User user;

}
