package project.rtc.communicator.messager.services;

import project.rtc.communicator.messager.entities.Message;
import project.rtc.infrastructure.exception.exceptions.RoomNotFoundException;
import project.rtc.infrastructure.exception.exceptions.UserNotFoundException;

import java.util.List;
import java.util.NoSuchElementException;

public interface MessageService {
	
	// This method allows you to save a new message for each transferred room.
	// Saving the message is validated.
	// Throw IllegalArgumentException if delivered message validation failed.
	// Throw NoSuchElementException if no room found.
	Message save(Message message) throws RoomNotFoundException, UserNotFoundException;
	
	// This method allows you to send messages to all users in the room through the use of websocket
	// The method accepts a message to be sent to users, the message must have an assigned room identifier, otherwise it will be thrown IllegalArgumentException
	// If no room is found by roomId throw NoSuchElementException
	List<String> send(String destination, Message message) throws NoSuchElementException, RoomNotFoundException, UserNotFoundException;

	// It allows you to set all messages in a given room to be read by the user.
	// The method returns a list of messages for which the change was made.
	List<Message> addReadBy(String roomId, String userNick);

}
