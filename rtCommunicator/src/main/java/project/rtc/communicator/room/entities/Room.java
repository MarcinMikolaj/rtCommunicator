package project.rtc.communicator.room.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.ElementCollection;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import project.rtc.communicator.messager.entities.Message;

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
	private List<Message> messages = new ArrayList<>();
	@ElementCollection
	@ToString.Exclude
	private List<String> usersId = new ArrayList<>();

	public Room(String roomId, String name) {
		super();
		this.roomId = roomId;
		this.name = name;
	}

	public Room(String roomId, String name, List<String> usersId) {
		super();
		this.roomId = roomId;
		this.name = name;
		this.usersId = usersId;
	}
}
