package project.rtc.communicator.room;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.ElementCollection;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import project.rtc.communicator.message.Message;
import project.rtc.test.user.User;

@Document
public class Room {
	
	@Id
	private String mongoId;
	private String roomId;
	private String name;
	
	@ElementCollection
	private List<User> users = new ArrayList<User>();
	
	@ElementCollection
	private List<Message> messages;
	
	public Room() {}
	
	
	public Room(String roomId) {
		super();
		this.roomId = roomId;
	}

	public Room(String roomId, List<User> users, List<Message> messages) {
		super();
		this.roomId = roomId;
		this.users = users;
		this.messages = messages;
	}

	
	public String getMongoId() {
		return mongoId;
	}

	
	public void setMongoId(String mongoId) {
		this.mongoId = mongoId;
	}

	
	public String getRoomId() {
		return roomId;
	}

	
	public void setRoomId(String roomId) {
		this.roomId = roomId;
	}

	
	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public List<User> getUsers() {
		return users;
	}


	public void setUsers(List<User> user) {
		this.users = user;
	}


	public List<Message> getMessages() {
		return messages;
	}

	
	public void setMessages(List<Message> messages) {
		this.messages = messages;
	}


	@Override
	public String toString() {
		return "Room [mongoId=" + mongoId + ", roomId=" + roomId + ", name=" + name + ", users=" + users + "]";
	}

}
