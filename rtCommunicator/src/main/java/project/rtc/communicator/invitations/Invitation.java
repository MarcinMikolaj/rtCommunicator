package project.rtc.communicator.invitations;

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
public class Invitation implements Serializable{
	
	private static final long serialVersionUID = -3445901393203661534L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String identificator;
	private String inviting;
	private String invited;
	private Date creation_date;
	
	public Invitation() {}
	
	public Invitation(String identificator, String inviting, String invited, Date creation_date) {
		super();
		this.identificator = identificator;
		this.inviting = inviting;
		this.invited = invited;
		this.creation_date = creation_date;
	}


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public String getIdentificator() {
		return identificator;
	}

	public void setIdentificator(String identificator) {
		this.identificator = identificator;
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

	public Date getCreation_date() {
		return creation_date;
	}

	public void setCreation_date(Date creation_date) {
		this.creation_date = creation_date;
	}

	@Override
	public String toString() {
		return "Invitation [id=" + id + ", inviting=" + inviting + ", invited=" + invited + ", creation_date="
				+ creation_date + "]";
	}

}
