package project.rtc.communicator.user;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.ElementCollection;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import project.rtc.registration.ProfilePicture;
import project.rtc.registration.validators.ExistsByEmail;
import project.rtc.registration.validators.ExistsByNick;

@Document
@Data
@NoArgsConstructor
public class User {
	
	@Id
	private String mongoId;

	@NotBlank(message = "Nick cannot be empty")
	@Pattern(regexp = "^[a-zA-Z0-9]*$", message = "Nick can only contain letters and numbers")
	@Size(max = 20, message = "Nickname can be up to 20 characters long")
	@ExistsByNick(message = "An account with the given nickname exists")
	private String nick;

	@NotBlank(message = "Email address cannot be empty")
	@Email(message = "The email address must be in the correct format")
	@ExistsByEmail(message = "An account with the given e-mail address already exists")
	private String email;

	private String pathToProfileImg;
	private ProfilePicture profilePicture;
	
	@ElementCollection
	private List<String> roomsId = new ArrayList<String>();
	
	@ElementCollection
	private List<String> invitations = new ArrayList<String>();

}
