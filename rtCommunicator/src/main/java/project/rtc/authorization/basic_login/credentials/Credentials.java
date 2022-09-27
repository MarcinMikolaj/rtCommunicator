package project.rtc.authorization.basic_login.credentials;

import java.util.Collection;
import java.util.Map;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import project.rtc.registration.validators.ExistsByEmail;
import project.rtc.registration.validators.Password;

@Entity 
@NamedQueries({  
	@NamedQuery(name = "Credentials.findById", query = "SELECT c FROM Credentials c WHERE c.id = :id"),
    @NamedQuery(name = "Credentials.findByEmail", query = "SELECT c FROM Credentials c WHERE c.email = :email"),
    @NamedQuery(name = "Credentials.updatePasswordByEmail", query = "UPDATE Credentials c SET c.password = :password WHERE c.email = :email"),
    @NamedQuery(name = "Credentials.existByEmail", query ="SELECT CASE WHEN count(c) > 0 THEN true ELSE false END FROM Credentials c where c.email = :email"),
    @NamedQuery(name = "Credentials.updateEmailById", query ="UPDATE Credentials c SET c.email = :email WHERE c.id = :id"),
    @NamedQuery(name = "Credentials.updatePasswordById", query ="UPDATE Credentials c SET c.password = :password WHERE c.id = :id")
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
	private boolean isAccountNonLocked;
	
	public Credentials() {}
	
	public Credentials(String password, String email, String name, String provider) {
		super();
		this.password = password;
		this.email = email;
		this.name = name;
		this.provider = provider;
		isAccountNonLocked = true;
		
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
		return isAccountNonLocked;
	}
	
	public void setIsAccountNonLocked(boolean isAccountNonLocked) {
		this.isAccountNonLocked = isAccountNonLocked;
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
