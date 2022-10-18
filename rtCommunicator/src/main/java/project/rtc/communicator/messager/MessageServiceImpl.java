package project.rtc.communicator.messager;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import project.rtc.communicator.room.Room;
import project.rtc.communicator.room.RoomRepository;
import project.rtc.communicator.room.RoomService;
import project.rtc.communicator.room.RoomServiceImpl;

@Service
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
public class MessageServiceImpl implements MessageService {
	
	private MessageRepository messageRepository;
	private RoomRepository roomRepository;
	private RoomService roomService;
	private SimpMessagingTemplate simpMessagingTemplate;
	private Validator validator;
	
	public MessageServiceImpl(MessageRepository messageRepository, RoomServiceImpl roomServiceImpl, RoomRepository roomRepository, SimpMessagingTemplate simpMessagingTemplate, Validator validator) {
		this.messageRepository = messageRepository;
		this.roomRepository = roomRepository;
		this.roomService = roomServiceImpl;
		this.simpMessagingTemplate = simpMessagingTemplate;
		this.validator = validator;
	}

	// This method allows you to save a new message for each transferred room.
	// Saving the message is validated.
	// Return saved message.
	// Throw IllegalArgumentException if delivered message validation failed.
	// Throw NoSuchElementException if no room found.
	@Override
	public Message save(Message message) {
		
		Room room;
		
		Set<ConstraintViolation<Message>> errors = validator.validate(message, Message.class);
		
		if(!errors.isEmpty()) {
			errors.stream().forEach(error -> System.out.println(error.getMessage()));
			throw new IllegalArgumentException("MessageServiceImpl.save: Message validation failed");
		}
		
		
		room = roomRepository.findByRoomId(message.getRoomId()).orElseThrow(() -> new NoSuchElementException("MessageServiceImpl.save: no room found"));
		room.getMessages().add(message);
		
		messageRepository.save(message);
		roomRepository.save(room);
		
		return message;
	}

	// This method allows you to send messages to all users in the room through the use of websocket
	// The method accepts a message to be sent to users, the message must have an assigned room identifier, otherwise it will be thrown IllegalArgumentException
	// If no room is found by roomId throw NoSuchElementException
	@Override
	public List<String> send(String destination, Message message) throws NoSuchElementException {
		
		if(message.getRoomId() == null || message.getRoomId().equals("")) {
			throw new IllegalArgumentException("MessageServiceImpl.send: The message must have a room ID assigned to it");
		}
		
		Room room;
		List<String> recipients = new ArrayList<String>();
		
		room = roomService.getRoom(message.getRoomId());
		
        room.getUsers().stream()
           .filter(u -> u != null)
           .filter(u -> !u.getNick().equals(message.getOwner()))
           .peek(u -> recipients.add(u.getEmail()))
           .peek(u -> simpMessagingTemplate.convertAndSendToUser(u.getEmail(), destination, message))
           .collect(Collectors.toList()).size();
       
		return recipients;
	}
	
	
}
