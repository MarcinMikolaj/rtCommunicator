package project.rtc.communicator.messager.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.web.bind.annotation.*;
import project.rtc.communicator.messager.entities.Message;
import project.rtc.infrastructure.exception.exceptions.MessageNotFoundException;
import project.rtc.infrastructure.exception.exceptions.NoAuthorizationTokenException;
import project.rtc.infrastructure.exception.exceptions.RoomNotFoundException;
import project.rtc.infrastructure.exception.exceptions.UserNotFoundException;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RequestMapping(value = "/app/api/message")
public interface MessageRestController {

    @MessageMapping("/messenger")
    void processMessageFromClient(@Payload Message message) throws RoomNotFoundException, UserNotFoundException;

    @GetMapping(value = "/get/page")
    ResponseEntity<?> getMessageFromRoom(@RequestParam(defaultValue = "1") int page
            , @RequestParam(defaultValue = "1") int size
            , @RequestParam() String roomId) throws RoomNotFoundException, MessageNotFoundException;

    @PostMapping(path = "/update/read")
    ResponseEntity<String> updateReadBy(@RequestBody Map<String, ?> roomId, HttpServletRequest httpServletRequest)
            throws UserNotFoundException, NoAuthorizationTokenException, MessageNotFoundException;
}
