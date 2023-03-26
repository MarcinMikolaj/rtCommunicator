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
import project.rtc.communicator.user.entities.User;
import project.rtc.infrastructure.exception.exceptions.MessageNotFoundException;
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
		message = setReadByAndMissedByForCreatedMessage(message, room.getRoomId());
		roomRepository.save(room);
		return messageRepository.save(message);
	}

	@Override
	public List<Message> getAllMessageFromRoom(String roomId) {
		return messageRepository.findAllByRoomId(roomId).orElse(new ArrayList<>());
	}

	@Override
	public List<String> send(String destination, Message message) throws RoomNotFoundException {
		if(message.getRoomId() == null || message.getRoomId().equals("")) throw new IllegalArgumentException();
		return roomService.getUsersFromRoom(message.getRoomId(), false).stream()
				.filter(Objects::nonNull)
				.filter(u -> !u.getNick().equals(message.getUserNick()))
				.peek(u -> simpMessagingTemplate.convertAndSendToUser(u.getEmail(), destination, message))
				.map(User::getEmail)
				.collect(Collectors.toList());
	}

	@Override
	public List<Message> updateAllMessageForRoomAsReadBy(String roomId, String userNick) throws MessageNotFoundException {
		List<Message> messages = messageRepository.findAllUnreadMessagesInARoom(roomId, userNick)
				.orElseThrow(MessageNotFoundException::new);
		return messageRepository.saveAll(messages.stream()
				.filter(m -> m.getMissedBy().contains(userNick))
				.filter(m -> !m.getReceivedBy().contains(userNick))
				.peek(m -> m.getMissedBy().removeIf(nick -> nick.equals(userNick)))
				.peek(m -> m.getReceivedBy().add(userNick))
				.collect(Collectors.toList()));
	}

	protected Message addUsersWhoNotReadMessage(Message message, List<String> nicks){
		nicks.stream()
				.filter(Objects::nonNull)
				.forEach(n -> message.getMissedBy().add(n));
		return message;
	}

	protected Message addUsersWhoReadMessage(Message message, List<String> nicks){
		nicks.stream()
				.filter(Objects::nonNull)
				.forEach(n -> message.getReceivedBy().add(n));
		return message;
	}

	private Message setReadByAndMissedByForCreatedMessage(Message message, String roomId) throws RoomNotFoundException {
		List<User> users = roomService.getUsersFromRoom(roomId, false);
		String authorNick = message.getUserNick();
		message = addUsersWhoNotReadMessage(message
				, users.stream()
						.map(User::getNick)
						.filter(n -> !n.equals(authorNick))
						.collect(Collectors.toList()));
		message = addUsersWhoReadMessage(message
				, Collections.singletonList(message.getUserNick()));
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
