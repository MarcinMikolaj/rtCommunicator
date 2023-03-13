package project.rtc.communicator.messager;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.ElementCollection;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

// Represents a message sent between users.
@Document
public class Message {
	
	@Id
	private String mongoId;
	
	private String roomId;
	
	// Indicates the nickname of the user who owns the message.
	private String owner;
	
	private String content;
	private String dateMilisecondsUTC;
	
	// Indicates the users who read this message.
	@ElementCollection
	private List<String> receivedBy = new ArrayList<String>();
	
	// Indicates the users who read this message.
	// After composing the message, this list is loaded with all indications of the users that belong to the room assigned to this message.
	@ElementCollection
	private List<String> missedBy = new ArrayList<String>();
	
	public Message() {
		super();
	}
	
	public Message(String roomId, String owner, String content, String dateMilisecondsUTC) {
		super();
		this.roomId = roomId;
		this.owner = owner;
		this.content = content;
		this.dateMilisecondsUTC = dateMilisecondsUTC;
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
	
	public String getOwner() {
		return owner;
	}
	
	public void setOwner(String owner) {
		this.owner = owner;
	}
	
	public String getContent() {
		return content;
	}
	
	public void setContent(String content) {
		this.content = content;
	}
	

	public String getDateMilisecondsUTC() {
		return dateMilisecondsUTC;
	}
	

	public void setDateMilisecondsUTC(String dateMilisecondsUTC) {
		this.dateMilisecondsUTC = dateMilisecondsUTC;
	}
	
	
	public List<String> userNick() {
		return receivedBy;
	}

	public void setReceivedBy(List<String> receivedBy) {
		this.receivedBy = receivedBy;
	}

	public List<String> getMissedBy() {
		return missedBy;
	}

	public void setMissedBy(List<String> missedBy) {
		this.missedBy = missedBy;
	}

	
	public List<String> getReceivedBy() {
		return receivedBy;
	}

	@Override
	public String toString() {
		return "Message [roomId=" + roomId + ", owner=" + owner + ", content=" + content + "]";
	}
	
}
