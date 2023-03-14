package project.rtc.communicator.messager.controllers.impl;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.nimbusds.jose.shaded.json.JSONObject;

import project.rtc.communicator.messager.dto.Message;
import project.rtc.communicator.messager.services.MessageService;
import project.rtc.communicator.messager.services.MessageServiceImpl;
import project.rtc.communicator.messager.controllers.MessageRestController;
import project.rtc.communicator.user.services.UserService;
import project.rtc.communicator.user.services.UserServiceImpl;
import project.rtc.exceptions.NoAuthorizationTokenException;
import project.rtc.exceptions.UserNotFoundException;

@RestController
public class MessageRestControllerImpl implements MessageRestController {
	
	private MessageService messageService;
	private UserService userService;
	
	public MessageRestControllerImpl(MessageServiceImpl messageServiceImpl, UserServiceImpl userServiceImpl) {
		this.messageService = messageServiceImpl;
		this.userService = userServiceImpl;
	}

	@Override
	@MessageMapping("/messenger")
	public void processMessageFromClient(@Payload Message message) {
		
		messageService.save(message);
		messageService.send("/queue/messages", message);
		
	}

	@Override
	@RequestMapping(path = "/app/message/update/readby", method = RequestMethod.POST)
	public ResponseEntity<String> updateReadBy(@RequestBody Map<String, ?> roomId, HttpServletRequest httpServletRequest) throws UserNotFoundException, NoAuthorizationTokenException {
		
		JSONObject jsonObject = new JSONObject(roomId);
		String id = jsonObject.getAsString("roomId");
		
		messageService.addReadBy(id, userService.getUser(httpServletRequest).getNick());
		
		return new ResponseEntity<String>("OK", HttpStatus.OK);
	 }
}
