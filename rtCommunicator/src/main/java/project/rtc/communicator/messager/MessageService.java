package project.rtc.communicator.messager;

import java.util.List;
import java.util.NoSuchElementException;

public interface MessageService {
	
	// This method allows you to save a new message for each transferred room.
	// Saving the message is validated.
	// Return saved message.
	// Throw IllegalArgumentException if delivered message validation failed.
	// Throw NoSuchElementException if no room found.
	public Message save(Message message);
	
	// This method allows you to send messages to all users in the room through the use of websocket
	// The method accepts a message to be sent to users, the message must have an assigned room identifier, otherwise it will be thrown IllegalArgumentException
	// If no room is found by roomId throw NoSuchElementException
	public List<String> send(String destination, Message message) throws NoSuchElementException;

}
