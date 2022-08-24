package project.rtc.authorization.basic_login.credentials;

import java.util.Collection;
import java.util.Map;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import project.rtc.authorization.oauth2.provider.AuthProvider;

@Entity
@NamedQueries({
    @NamedQuery(name = "Credentials.findByEmail", query = "SELECT c FROM Credentials c WHERE c.email = :email"),
    @NamedQuery(name = "Credentials.updatePasswordByEmail", query = "UPDATE Credentials c SET c.password = :password WHERE c.email = :email")
})
@Table(name = "credentials")
public class Credentials implements UserDetails, OAuth2User {
	
	private static final long serialVersionUID = -7340660922729835855L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String password;
	private String email;
	private String name;
	private String provider;
	
	public Credentials() {}
	
	public Credentials(String password, String email, String name, String provider) {
		super();
		this.password = password;
		this.email = email;
		this.name = name;
		this.provider = provider;
	}
	
	
	public Credentials(String password, String email, String name) {
		super();
		this.password = password;
		this.email = email;
		this.name = name;
		this.provider = AuthProvider.local.toString();
	}
	
	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return null;
	}

	@Override
	public String getUsername() {
		return email;
	}
	
	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	@Override
	public Map<String, Object> getAttributes() {
		return null;
	}
	
	
}