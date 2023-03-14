package project.rtc.communicator.room.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import project.rtc.communicator.room.dto.RoomRequestPayload;
import project.rtc.communicator.room.dto.RoomResponsePayload;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

public interface RoomRestController {

    ResponseEntity<RoomResponsePayload> createRoomWithAuthoringUser(@RequestBody RoomRequestPayload roomRequest, HttpServletRequest httpServletRequest) throws ServletException;

    ResponseEntity<RoomResponsePayload> addNewUserToRoom(@RequestBody RoomRequestPayload roomRequest, HttpServletRequest httpServletRequest);

    ResponseEntity<RoomResponsePayload> getRooms(@RequestBody RoomRequestPayload roomRequest, HttpServletRequest httpServletRequest) throws ServletException;

    ResponseEntity<RoomResponsePayload> removeRoom(@RequestBody RoomRequestPayload roomRequest, HttpServletRequest httpServletRequest) throws ServletException;

    ResponseEntity<RoomResponsePayload> renameRoom(@RequestBody RoomRequestPayload roomRequest, HttpServletRequest httpServletRequest) throws ServletException;

    ResponseEntity<RoomResponsePayload> removeUserFromRoom(@RequestBody RoomRequestPayload roomRequest, HttpServletRequest httpServletRequest) throws ServletException;

    ResponseEntity<RoomResponsePayload> leaveRoom(@RequestBody RoomRequestPayload roomRequest, HttpServletRequest httpServletRequest) throws ServletException;
}
