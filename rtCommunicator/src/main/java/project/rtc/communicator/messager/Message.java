package project.rtc.communicator.messager;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Message {
	
	@Id
	private String mongoId;
	
	//The passed identifier(roomId) cannot be empty
	private String roomId;
	private String owner;
	private String content;
	private String dateMilisecondsUTC;
	
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

	@Override
	public String toString() {
		return "Message [roomId=" + roomId + ", owner=" + owner + ", content=" + content + "]";
	}
	
}
