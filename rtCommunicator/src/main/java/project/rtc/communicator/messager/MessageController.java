package project.rtc.communicator.messager;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Controller;

@Controller
public class MessageController {
	
	private MessageService messageService;
	
	public MessageController(MessageServiceImpl messageServiceImpl) {
		this.messageService = messageServiceImpl;
	}
	
	
	@MessageMapping("/messenger")
	public void processMessageFromClient(@Payload Message message) {
		
		messageService.save(message);
		messageService.send("/queue/messages", message);
		
	}

}
