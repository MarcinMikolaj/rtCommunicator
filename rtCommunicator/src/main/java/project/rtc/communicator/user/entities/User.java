package project.rtc.communicator.user.entities;

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

import project.rtc.registration.dto.ProfilePicture;
import project.rtc.infrastructure.validators.user.ExistsByEmail;
import project.rtc.infrastructure.validators.user.ExistsByNick;

@Document
@Data
@NoArgsConstructor
public class User {
	
	@Id
	private String mongoId;
	private String userId;
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
	private List<String> roomsId = new ArrayList<>();
	@ElementCollection
	private List<String> invitations = new ArrayList<>();

	public User(String userId, String nick, String email, String pathToProfileImg) {
		this.userId = userId;
		this.nick = nick;
		this.email = email;
		this.pathToProfileImg = pathToProfileImg;
	}
}
