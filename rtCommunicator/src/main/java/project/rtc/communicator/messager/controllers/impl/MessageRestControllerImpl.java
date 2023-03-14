package project.rtc.communicator.messager.controllers.impl;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.web.bind.annotation.*;

import com.nimbusds.jose.shaded.json.JSONObject;

import project.rtc.communicator.messager.dto.Message;
import project.rtc.communicator.messager.services.MessageService;
import project.rtc.communicator.messager.controllers.MessageRestController;
import project.rtc.communicator.user.services.UserService;
import project.rtc.exceptions.NoAuthorizationTokenException;
import project.rtc.exceptions.UserNotFoundException;

@RestController
@RequiredArgsConstructor
public class MessageRestControllerImpl implements MessageRestController {
	
	private final MessageService messageService;
	private final UserService userService;
	

	@Override
	@MessageMapping("/messenger")
	public void processMessageFromClient(@Payload Message message) {
		
		messageService.save(message);
		messageService.send("/queue/messages", message);
		
	}

	@Override
	@PostMapping(path = "/app/message/update/readby")
	public ResponseEntity<String> updateReadBy(@RequestBody Map<String, ?> roomId, HttpServletRequest httpServletRequest) throws UserNotFoundException, NoAuthorizationTokenException {
		
		JSONObject jsonObject = new JSONObject(roomId);
		String id = jsonObject.getAsString("roomId");
		
		messageService.addReadBy(id, userService.getUser(httpServletRequest).getNick());
		
		return new ResponseEntity<String>("OK", HttpStatus.OK);
	 }
}
