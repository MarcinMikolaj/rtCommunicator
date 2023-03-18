package project.rtc.communicator.room.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import project.rtc.communicator.room.dto.RoomRequestPayload;
import project.rtc.communicator.room.dto.RoomResponsePayload;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

// Is responsible for handling requests regarding room management in the application.
@RequestMapping(value = "/app/rtc/room")
public interface RoomRestController {

    @PostMapping(path = "/create")
    ResponseEntity<RoomResponsePayload> createRoomWithAuthoringUser(@RequestBody RoomRequestPayload roomRequest
            , HttpServletRequest httpServletRequest) throws ServletException;

    @PostMapping(path = "/user/add")
    ResponseEntity<RoomResponsePayload> addNewUserToRoom(@RequestBody RoomRequestPayload roomRequest
            , HttpServletRequest httpServletRequest);

    @PostMapping(path = "/get")
    ResponseEntity<RoomResponsePayload> getRooms(@RequestBody RoomRequestPayload roomRequest
            , HttpServletRequest httpServletRequest) throws ServletException;

    @PostMapping(path = "/remove")
    ResponseEntity<RoomResponsePayload> removeRoom(@RequestBody RoomRequestPayload roomRequest
            , HttpServletRequest httpServletRequest) throws ServletException;

    @PostMapping(path = "/name/update")
    ResponseEntity<RoomResponsePayload> renameRoom(@RequestBody RoomRequestPayload roomRequest
            , HttpServletRequest httpServletRequest) throws ServletException;

    @PostMapping(path = "/user/remove")
    ResponseEntity<RoomResponsePayload> removeUserFromRoom(@RequestBody RoomRequestPayload roomRequest
            , HttpServletRequest httpServletRequest) throws ServletException;

    @PostMapping(path = "/user/leave")
    ResponseEntity<RoomResponsePayload> leaveRoom(@RequestBody RoomRequestPayload roomRequest
            , HttpServletRequest httpServletRequest) throws ServletException;
}
