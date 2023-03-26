package project.rtc.communicator.messager.services.impl;

import java.util.*;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import project.rtc.communicator.messager.entities.Message;
import project.rtc.communicator.messager.repositories.MessageRepository;
import project.rtc.communicator.messager.services.MessageService;
import project.rtc.communicator.room.entities.Room;
import project.rtc.communicator.room.repositories.RoomRepository;
import project.rtc.communicator.room.service.RoomService;
import project.rtc.infrastructure.exception.exceptions.RoomNotFoundException;
import project.rtc.infrastructure.exception.exceptions.UserNotFoundException;

@Service
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {

	private final SimpMessagingTemplate simpMessagingTemplate;
	private final MessageRepository messageRepository;
	private final RoomRepository roomRepository;
	private final RoomService roomService;

	@Override
	public Message create(Message message) throws RoomNotFoundException, UserNotFoundException {
		Room room = roomService.getRoom(message.getRoomId());
		message.setMessageId(generateUniqueId());
		room.getMessagesId().add(message.getMessageId());
		message = loadMissedByList(message, room);
		roomRepository.save(room);
		return messageRepository.save(message);
	}

	@Override
	public List<Message> getAllMessageFromRoom(String roomId) {
		return messageRepository.findAllByRoomId(roomId).orElse(new ArrayList<>());
	}

	@Override
	public List<String> send(String destination, Message message) throws NoSuchElementException, RoomNotFoundException, UserNotFoundException {
		if(message.getRoomId() == null || message.getRoomId().equals(""))
			throw new IllegalArgumentException("MessageServiceImpl.send: The message must have a room ID assigned to it");
		List<String> recipients = new ArrayList<>();
		roomService.getUsersFromRoom(message.getRoomId(), false).stream()
           .filter(u -> u != null)
           .filter(u -> !u.getNick().equals(message.getUserNick()))
           .peek(u -> recipients.add(u.getEmail()))
           .peek(u -> simpMessagingTemplate.convertAndSendToUser(u.getEmail(), destination, message))
           .collect(Collectors.toList()).size();
		return recipients;
	}

	@Override
	public List<Message> addReadBy(String roomId, String userNick) {
		List<Message> messages = getAllMessageFromRoom(roomId);
		for(Message m: messages) {
			if(m.getMissedBy().contains(userNick) && !m.getReceivedBy().contains(userNick)) {
				m.getMissedBy().removeIf(nick -> nick.equals(userNick));
				m.getReceivedBy().add(userNick);
				messageRepository.save(m);
			}
		}
		return messages;
	}

	// This method allow set message as not read by others user except sending users.
	// This method need Room object to get users list.
	private Message loadMissedByList(Message message, Room room) throws RoomNotFoundException {
		roomService.getUsersFromRoom(room.getRoomId(), false).stream()
				.filter(u -> u != null)
				.filter(u -> u.getNick() != null)
				.filter(u -> !u.getNick().equals(message.getUserNick()))
				.peek(u -> message.getMissedBy().add(u.getNick()))
				.collect(Collectors.toList())
				.size();

		message.getReceivedBy().add(message.getUserNick());
		return message;
	}

	// Generate unique ID as actual date in milliseconds + random UUID
	private String generateUniqueId() {
		return getDateNowInMilliseconds() + "-" + UUID.randomUUID();
	}

	protected String getDateNowInMilliseconds(){
		Date date = new Date();
		return Long.toString(date.getTime());
	}

}
