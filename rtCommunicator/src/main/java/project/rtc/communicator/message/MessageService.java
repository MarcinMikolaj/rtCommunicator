package project.rtc.communicator.message;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import project.rtc.communicator.room.Room;
import project.rtc.communicator.room.RoomRepository;

@Service
public class MessageService {
	
	private MessageRepository messageRepository;
	private RoomRepository roomRepository;
	
	@Autowired
	public void setMessageRepository(MessageRepository messageRepository) {
		this.messageRepository = messageRepository;
	}
	
	@Autowired
	public void setRoomRepository(RoomRepository roomRepository) {
		this.roomRepository = roomRepository;
	}
	
	public void addMessageToRoom(Message message, String roomId) {
		
		Room room;
		
		room = roomRepository.findByRoomId(roomId);
		
		room.getMessages().add(message);
		
		messageRepository.save(message);
		roomRepository.save(room);
	}

}