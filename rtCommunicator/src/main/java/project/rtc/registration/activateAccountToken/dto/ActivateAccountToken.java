package project.rtc.registration.activateAccountToken.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

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
@NoArgsConstructor
@ToString
@Getter
@Setter
public class ActivateAccountToken {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String email;

	private String token;

	private Timestamp created_on;

	private Timestamp expiration_time;

	public ActivateAccountToken(String email, String token, Timestamp created_on, Timestamp expiration_time) {
		super();
		this.email = email;
		this.token = token;
		this.created_on = created_on;
		this.expiration_time = expiration_time;
	}

}
