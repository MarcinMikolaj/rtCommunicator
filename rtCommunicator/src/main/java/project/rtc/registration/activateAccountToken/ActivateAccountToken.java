package project.rtc.registration.activateAccountToken;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "activate_account_token")
@NamedQueries({
	@NamedQuery(name = "ActivateAccountToken.delete", query = "DELETE FROM ActivateAccountToken a WHERE a.email = :email"),
	@NamedQuery(name = "ActivateAccountToken.findByToken", query = "SELECT a FROM ActivateAccountToken a WHERE a.token = :token")
})
public class ActivateAccountToken {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String email;
	private String token;
	private Timestamp created_on;
	private Timestamp expiration_time;
	
	public ActivateAccountToken() {
		super();
	}


	public ActivateAccountToken(String email, String token, Timestamp created_on, Timestamp expiration_time) {
		super();
		this.email = email;
		this.token = token;
		this.created_on = created_on;
		this.expiration_time = expiration_time;
	}


	
	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public String getToken() {
		return token;
	}


	public void setToken(String token) {
		this.token = token;
	}


	public Timestamp getCreated_on() {
		return created_on;
	}


	public void setCreated_on(Timestamp created_on) {
		this.created_on = created_on;
	}


	public Timestamp getExpiration_time() {
		return expiration_time;
	}


	public void setExpiration_time(Timestamp expiration_time) {
		this.expiration_time = expiration_time;
	}


	@Override
	public String toString() {
		return "ActivateAccountToken [id=" + id + ", email=" + email + ", token=" + token + ", created_on=" + created_on
				+ ", expiration_time=" + expiration_time + "]";
	}
	
}
