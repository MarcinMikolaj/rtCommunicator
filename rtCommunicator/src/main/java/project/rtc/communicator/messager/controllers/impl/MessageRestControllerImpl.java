package project.rtc.communicator.messager.controllers.impl;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.nimbusds.jose.shaded.json.JSONObject;

import project.rtc.communicator.messager.entities.Message;
import project.rtc.communicator.messager.services.MessageService;
import project.rtc.communicator.messager.controllers.MessageRestController;
import project.rtc.communicator.user.services.UserService;
import project.rtc.infrastructure.exception.exceptions.MessageNotFoundException;
import project.rtc.infrastructure.exception.exceptions.NoAuthorizationTokenException;
import project.rtc.infrastructure.exception.exceptions.RoomNotFoundException;
import project.rtc.infrastructure.exception.exceptions.UserNotFoundException;

@RestController
@RequiredArgsConstructor
public class MessageRestControllerImpl implements MessageRestController {
	
	private final MessageService messageService;
	private final UserService userService;

	@Override
	public void processMessageFromClient(Message message) throws RoomNotFoundException, UserNotFoundException {
		messageService.create(message);
		messageService.send("/queue/messages", message);
	}

	public ResponseEntity<?> getMessageFromRoom(@RequestParam(defaultValue = "0") int page
			, @RequestParam(defaultValue = "3") int size
			, @RequestParam() String roomId) throws MessageNotFoundException {
		return new ResponseEntity<>(messageService.getMessagePage(roomId, page, size), HttpStatus.OK);
	}

	@Override
	public ResponseEntity<String> updateReadBy(Map<String, ?> roomId, HttpServletRequest httpServletRequest)
			throws UserNotFoundException, NoAuthorizationTokenException, MessageNotFoundException {
		JSONObject jsonObject = new JSONObject(roomId);
		String id = jsonObject.getAsString("roomId");
		messageService.updateAllMessageForRoomAsReadBy(id, userService.getUser(httpServletRequest).getNick());
		return new ResponseEntity<>(HttpStatus.OK);
	 }
}
