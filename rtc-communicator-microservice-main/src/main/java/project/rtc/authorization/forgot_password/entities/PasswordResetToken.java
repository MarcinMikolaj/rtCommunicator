package project.rtc.authorization.forgot_password.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.sql.Timestamp;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "reset_password_token")
@NamedQueries({
	@NamedQuery(name = "PasswordResetToken.findByToken", query = "SELECT p FROM PasswordResetToken p WHERE p.token = :token"),
	@NamedQuery(name = "PasswordResetToken.removeByToken", query = "DELETE FROM PasswordResetToken p WHERE p.token = :token")
})
@NoArgsConstructor
@Getter
@Setter
@ToString
public class PasswordResetToken implements Serializable {
	private static final long serialVersionUID = -5688020902433665069L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String email;
	private String token;
	private Timestamp create_on;
	private Timestamp expiration_time;

	public PasswordResetToken(String email, String token, Timestamp create_on, Timestamp expiration_time) {
		this.email = email;
		this.token = token;
		this.create_on = create_on;
		this.expiration_time = expiration_time;
	}
}
