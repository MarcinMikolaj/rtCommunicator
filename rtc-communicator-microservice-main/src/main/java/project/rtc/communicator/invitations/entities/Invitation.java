package project.rtc.communicator.invitations.entities;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.annotation.Id;

@Document
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Invitation {
	@Id
	private String mongoId;
	private String invitationId;
	private String roomId;

	// Inviting and invited points to user nick.
	private String inviting;
	private String invited;
	private String creation_date;
	
	public Invitation(String invitationId, String roomId, String inviting, String invited, String creation_date) {
		super();
		this.invitationId = invitationId;
		this.roomId = roomId;
		this.inviting = inviting;
		this.invited = invited;
		this.creation_date = creation_date;
	}
}
