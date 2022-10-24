package project.rtc.communicator.messager;

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

import project.rtc.communicator.user.UserService;
import project.rtc.communicator.user.UserServiceImpl;
import project.rtc.exceptions.NoAuthorizationTokenException;
import project.rtc.exceptions.UserNotFoundException;

@RestController
public class MessageController {
	
	private MessageService messageService;
	private UserService userService;
	
	public MessageController(MessageServiceImpl messageServiceImpl, UserServiceImpl userServiceImpl) {
		this.messageService = messageServiceImpl;
		this.userService = userServiceImpl;
	}
	
	@MessageMapping("/messenger")
	public void processMessageFromClient(@Payload Message message) {
		
		messageService.save(message);
		messageService.send("/queue/messages", message);
		
	}
	
	@RequestMapping(path = "/app/message/update/readby", method = RequestMethod.POST)
	public ResponseEntity<String> updateReadBy(@RequestBody Map<String, ?> roomId, HttpServletRequest httpServletRequest) throws UserNotFoundException, NoAuthorizationTokenException {
		
		JSONObject jsonObject = new JSONObject(roomId);
		String id = jsonObject.getAsString("roomId");
		
		messageService.addReadBy(id, userService.getUser(httpServletRequest).getNick());
		
		return new ResponseEntity<String>("OK", HttpStatus.OK);
	 }
}
