package project.rtc.communicator.messager;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.ElementCollection;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

// Represents a message sent between users.
@Document
@NoArgsConstructor
@Getter
@Setter
@ToString
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
	@ToString.Exclude
	private List<String> receivedBy = new ArrayList<String>();
	
	// Indicates the users who read this message.
	// After composing the message, this list is loaded with all indications of the users that belong to the room assigned to this message.
	@ElementCollection
	@ToString.Exclude
	private List<String> missedBy = new ArrayList<String>();

	public Message(String roomId, String owner, String content, String dateMilisecondsUTC) {
		super();
		this.roomId = roomId;
		this.owner = owner;
		this.content = content;
		this.dateMilisecondsUTC = dateMilisecondsUTC;
	}

}
