package project.rtc.communicator.invitations.entities;

import lombok.*;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "invitation")
@NamedQueries({
	@NamedQuery(name ="Invitation.findByInvitationId", query = "SELECT i FROM Invitation i WHERE i.invitationId = :invitationId"),
	@NamedQuery(name ="Invitation.findByInvited", query = "SELECT i FROM Invitation i WHERE i.invited = :invited"),
	@NamedQuery(name = "Invitation.removeByInvitationId", query = "DELETE FROM Invitation i WHERE i.invitationId = :invitationId")
})
@NoArgsConstructor
@Getter
@Setter
@ToString
// inviting and invited points to user nick
public class Invitation implements Serializable{
	
	private static final long serialVersionUID = -3445901393203661534L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String invitationId;
	private String roomId;
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
