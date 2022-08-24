package project.rtc.authorization.forgot_password.reset_password_token;

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

public class PasswordResetToken implements Serializable {
	
	private static final long serialVersionUID = -5688020902433665069L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String email;
	private String token;
	private Timestamp create_on;
	private Timestamp expiration_time;
	
	public PasswordResetToken() {
		super();
	}

	public PasswordResetToken(String email, String token, Timestamp create_on, Timestamp expiration_time) {
		super();
		this.email = email;
		this.token = token;
		this.create_on = create_on;
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
	
	public Timestamp getCreate_on() {
		return create_on;
	}

	public void setCreate_on(Timestamp create_on) {
		this.create_on = create_on;
	}

	public Timestamp getExpiration_time() {
		return expiration_time;
	}

	public void setExpiration_time(Timestamp expiration_time) {
		this.expiration_time = expiration_time;
	}


	@Override
	public String toString() {
		return "ResetPasswordToken [id=" + id + ", email=" + email + ", create_on=" + create_on + ", expiration_time="
				+ expiration_time + "]";
	}
	
}
