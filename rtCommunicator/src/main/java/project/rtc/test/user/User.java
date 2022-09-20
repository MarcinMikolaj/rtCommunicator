package project.rtc.test.user;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.ElementCollection;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import project.rtc.registration.ProfilePicture;

@Document
public class User {
	
	@Id
	private String mongoId;
	private String email;
	private String nick;
	private String pathToProfileImg;
	private ProfilePicture profilePicture;
	
	@ElementCollection
	private List<String> roomsId = new ArrayList<String>();
	
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

	
	@Override
	public String toString() {
		return "User [mongoId=" + mongoId + ", email=" + email + ", nick=" + nick + ", pathToProfileImg="
				+ pathToProfileImg + ", profilePicture=" + profilePicture + ", roomsId=" + roomsId + "]";
	}

}
