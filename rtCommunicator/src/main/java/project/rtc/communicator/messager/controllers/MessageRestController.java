package project.rtc.communicator.messager.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import project.rtc.communicator.messager.entities.Message;
import project.rtc.infrastructure.exception.exceptions.NoAuthorizationTokenException;
import project.rtc.infrastructure.exception.exceptions.RoomNotFoundException;
import project.rtc.infrastructure.exception.exceptions.UserNotFoundException;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public interface MessageRestController {

    @MessageMapping("/messenger")
    void processMessageFromClient(@Payload Message message) throws RoomNotFoundException, UserNotFoundException;

    @PostMapping(path = "/app/message/update/readby")
    ResponseEntity<String> updateReadBy(@RequestBody Map<String, ?> roomId, HttpServletRequest httpServletRequest)
            throws UserNotFoundException, NoAuthorizationTokenException;
}
