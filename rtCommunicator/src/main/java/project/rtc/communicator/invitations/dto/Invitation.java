package project.rtc.communicator.invitations.dto;

import lombok.*;

import java.io.Serializable;
import java.sql.Date;

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
	@NamedQuery(name ="Invitation.findByIdentificator", query = "SELECT i FROM Invitation i WHERE i.identificator = :identificator"),
	@NamedQuery(name ="Invitation.findByInvited", query = "SELECT i FROM Invitation i WHERE i.invited = :invited"),
	@NamedQuery(name = "Invitation.removeByIdentificator", query = "DELETE FROM Invitation i WHERE i.identificator = :identyficator")
})
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Invitation implements Serializable{
	
	private static final long serialVersionUID = -3445901393203661534L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String identificator;
	private String inviting;
	private String invited;
	private Date creation_date;
	
	public Invitation(String identificator, String inviting, String invited, Date creation_date) {
		super();
		this.identificator = identificator;
		this.inviting = inviting;
		this.invited = invited;
		this.creation_date = creation_date;
	}

}
