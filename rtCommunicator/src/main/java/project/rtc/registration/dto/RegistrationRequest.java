package project.rtc.registration.dto;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import project.rtc.registration.dto.ProfilePicture;
import project.rtc.registration.validators.ExistsByEmail;
import project.rtc.registration.validators.ExistsByNick;
import project.rtc.registration.validators.Password;

// Class represents the user's request to create an account in application
@AllArgsConstructor
@Getter
public final class RegistrationRequest {

	@NotBlank(message = "Email address cannot be empty")
	@Email(message = "The email address must be in the correct format")
	@ExistsByEmail(message = "An account with the given e-mail address already exists")
	private final String email;
	
	@NotBlank(message = "Nick cannot be empty")
	@Pattern(regexp = "^[a-zA-Z0-9]*$", message = "Nick can only contain letters and numbers")
	@Size(max = 15, message = "Nickname can be up to 15 characters long")
	@ExistsByNick(message = "An account with the given nickname exists")
	private final String nick;
	
	@Password(message = "The specified password is incorrect")
	private final String password;
	
	@NotBlank
	private final String provider;
	
	@AssertTrue(message = "You must agree all statements")
	private final boolean statements;

	@ToString.Exclude
	private final ProfilePicture picture;

}
