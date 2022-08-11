package project.rtc.authorization.credentials;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import project.rtc.oauth2.AuthProvider;

@Entity
@NamedQueries({
	@NamedQuery(name = "Credentials.findByLogin", query = "SELECT c FROM Credentials c WHERE c.login = :login"),
    @NamedQuery(name = "Credentials.findByEmail", query = "SELECT c FROM Credentials c WHERE c.email = :email")
})
@Table(name = "credentials")
public class Credentials implements Serializable {
	
	private static final long serialVersionUID = -7340660922729835855L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String login;
	private String password;
	private String email;
	private String name;
	private String provider;
	
	public Credentials() {}
	
	public Credentials(String login, String password) {
		super();
		this.login = login;
		this.password = password;
		this.provider = AuthProvider.local.toString();
	}
	
	public String getLogin() {
		return login;
	}
	
	public void setLogin(String login) {
		this.login = login;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	
	public String getProvider() {
		return provider;
	}

	public void setProvider(String provider) {
		this.provider = provider;
	}
	
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "Account [login=" + login + ", password=" + password + "]";
	}

}
