package project.rtc.communicator.user;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.ElementCollection;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import project.rtc.registration.ProfilePicture;
import project.rtc.registration.validators.ExistsByEmail;
import project.rtc.registration.validators.ExistsByNick;

@Document
public class User {
	
	@Id
	private String mongoId;
	
	@NotBlank(message = "Email address cannot be empty")
	@Email(message = "The email address must be in the correct format")
	@ExistsByEmail(message = "An account with the given e-mail address already exists")
	private String email;
	
	@NotBlank(message = "Nick cannot be empty")
	@Pattern(regexp = "^[a-zA-Z0-9]*$", message = "Nick can only contain letters and numbers")
	@Size(max = 20, message = "Nickname can be up to 20 characters long")
	@ExistsByNick(message = "An account with the given nickname exists")
	private String nick;
	
	private String pathToProfileImg;
	private ProfilePicture profilePicture;
	
	@ElementCollection
	private List<String> roomsId = new ArrayList<String>();
	
	@ElementCollection
	private List<String> invitations = new ArrayList<String>();
	
	public User() {}
	
	public User(String nick, String email, String pathToProfileImg, ProfilePicture profilePicture, List<String> roomsId) {
		super();
		this.nick = nick;
		this.email = email;
		this.pathToProfileImg = pathToProfileImg;
		this.profilePicture = profilePicture;
		this.roomsId = roomsId;
	}


	public String getMongoId() {
		return mongoId;
	}

	
	public void setMongoId(String mongoId) {
		this.mongoId = mongoId;
	}

	
	public String getNick() {
		return nick;
	}

	
	public void setNick(String nick) {
		this.nick = nick;
	}

	public String getEmail() {
		return email;
	}

	
	public void setEmail(String email) {
		this.email = email;
	}

	
	public String getPathToProfileImg() {
		return pathToProfileImg;
	}
	

	public void setPathToProfileImg(String pathToProfileImg) {
		this.pathToProfileImg = pathToProfileImg;
	}

	
	public ProfilePicture getProfilePicture() {
		return profilePicture;
	}
	

	public void setProfilePicture(ProfilePicture profilePicture) {
		this.profilePicture = profilePicture;
	}

	
	public List<String> getRoomsId() {
		return roomsId;
	}

	
	public void setRoomsId(List<String> roomsId) {
		this.roomsId = roomsId;
	}

	
	public List<String> getInvitations() {
		return invitations;
	}

	public void setInvitations(List<String> invitations) {
		this.invitations = invitations;
	}
	
	
	@Override
	public String toString() {
		return "User [mongoId=" + mongoId + ", email=" + email + ", nick=" + nick + ", pathToProfileImg="
				+ pathToProfileImg + ", profilePicture=" + profilePicture + ", roomsId=" + roomsId + "]";
	}

}
