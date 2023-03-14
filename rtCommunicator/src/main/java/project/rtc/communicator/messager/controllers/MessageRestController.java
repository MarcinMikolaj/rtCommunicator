package project.rtc.communicator.messager.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.web.bind.annotation.RequestBody;
import project.rtc.communicator.messager.dto.Message;
import project.rtc.exceptions.NoAuthorizationTokenException;
import project.rtc.exceptions.UserNotFoundException;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public interface MessageRestController {

    void processMessageFromClient(@Payload Message message);

    ResponseEntity<String> updateReadBy(@RequestBody Map<String, ?> roomId, HttpServletRequest httpServletRequest) throws UserNotFoundException, NoAuthorizationTokenException;
}
