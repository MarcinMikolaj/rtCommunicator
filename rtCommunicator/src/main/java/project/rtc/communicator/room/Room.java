package project.rtc.communicator.room;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.ElementCollection;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import project.rtc.communicator.messager.Message;
import project.rtc.communicator.user.User;

@Document
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Room {
	
	@Id
	private String mongoId;
	private String roomId;
	private String name;
	
	@ElementCollection
	@ToString.Exclude
	private List<User> users = new ArrayList<User>();
	
	@ElementCollection
	@ToString.Exclude
	private List<Message> messages = new ArrayList<Message>();

	public Room(String roomId, String name) {
		super();
		this.roomId = roomId;
		this.name = name;
	}

}
