package project.rtc.registration;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import project.rtc.registration.validators.ExistsByEmail;
import project.rtc.registration.validators.ExistsByNick;
import project.rtc.registration.validators.Password;

// Class represents the user's request to create an account in application
public final class RegistrationRequest {

	@NotBlank(message = "Email address cannot be empty")
	@Email(message = "The email address must be in the correct format")
	@ExistsByEmail(message = "An account with the given e-mail address already exists")
	private final String email;
	
	@NotBlank(message = "Nick cannot be empty")
	@Pattern(regexp = "^[a-zA-Z0-9]*$", message = "Nick can only contain letters and numbers")
	@Size(max = 20, message = "Nickname can be up to 20 characters long")
	@ExistsByNick(message = "An account with the given nickname exists")
	private final String nick;
	
	@Password(message = "The specified password is incorrect")
	private final String password;
	
	@NotBlank
	private final String provider;
	
	@AssertTrue(message = "You must agree all statements")
	private final boolean statements;
	
	private final ProfilePicture picture;
	
	public RegistrationRequest(String email, String nick, String password, String provider, boolean statements,  ProfilePicture picture) {
		super();
		this.email = email;
		this.nick = nick;
		this.password = password;
		this.provider = provider;
		this.statements = statements;
		this.picture = picture;
	}

	
	public String getEmail() {
		return email;
	}
	
	public String getNick() {
		return nick;
	}
	
	
	public String getPassword() {
		return password;
	}
	

	public String getProvider() {
		return provider;
	}
	
	
	public ProfilePicture getPicture() {
		return picture;
	}

	
	public boolean isStatements() {
		return statements;
	}
	

	@Override
	public String toString() {
		return "RegistrationRequest [email=" + email + ", nick=" + nick + ", password=" + password + ", provider="
				+ provider + ", statements=" + statements + ", picture=" + picture.getName() + "]";
	}

}
