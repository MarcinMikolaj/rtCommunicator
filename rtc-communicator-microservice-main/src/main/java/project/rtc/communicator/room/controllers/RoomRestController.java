package project.rtc.communicator.room.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import project.rtc.infrastructure.exception.exceptions.MessageNotFoundException;
import project.rtc.infrastructure.exception.exceptions.RoomNotFoundException;
import project.rtc.communicator.room.dto.RoomRequestDto;
import project.rtc.infrastructure.exception.exceptions.NoAuthorizationTokenException;
import project.rtc.infrastructure.exception.exceptions.UserNotFoundException;
import project.rtc.infrastructure.groups.room.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RequestMapping(value = "/app/rtc/room")
public interface RoomRestController {

    @PostMapping(path = "/create")
    ResponseEntity<?> createRoom(@RequestBody @Validated(CreateRoomGroup.class) RoomRequestDto dto
            , HttpServletRequest httpServletRequest) throws NoAuthorizationTokenException, UserNotFoundException,
            MethodArgumentNotValidException, RoomNotFoundException;

    @PostMapping(path = "/user/add")
    ResponseEntity<?> addUserToRoom(@RequestBody @Validated(AddUserToRoomGroup.class) RoomRequestDto dto
            , HttpServletRequest httpServletRequest) throws NoAuthorizationTokenException, UserNotFoundException,
            MethodArgumentNotValidException, RoomNotFoundException;

    @PostMapping(path = "/get")
    ResponseEntity<?> getRooms(@RequestBody @Valid RoomRequestDto dto
            , HttpServletRequest httpServletRequest) throws NoAuthorizationTokenException, UserNotFoundException,
            MethodArgumentNotValidException, RoomNotFoundException, MessageNotFoundException;

    @PostMapping(path = "/remove")
    ResponseEntity<?> removeRoom(@RequestBody @Validated(RemoveRoomGroup.class) RoomRequestDto dto
            , HttpServletRequest httpServletRequest) throws NoAuthorizationTokenException, UserNotFoundException,
            MethodArgumentNotValidException, RoomNotFoundException;

    @PostMapping(path = "/name/update")
    ResponseEntity<?> renameRoom(@RequestBody @Validated(RenameRoomNameGroup.class) RoomRequestDto dto
            , HttpServletRequest httpServletRequest) throws NoAuthorizationTokenException, UserNotFoundException,
            MethodArgumentNotValidException, RoomNotFoundException;

    @PostMapping(path = "/user/remove")
    ResponseEntity<?> removeUserFromRoom(@RequestBody @Validated(RemoveUserFromRoomGroup.class) RoomRequestDto dto
            , HttpServletRequest httpServletRequest) throws NoAuthorizationTokenException, UserNotFoundException,
            MethodArgumentNotValidException, RoomNotFoundException;

    @PostMapping(path = "/user/leave")
    ResponseEntity<?> leaveRoom(@RequestBody @Validated(LeaveRoomGroups.class) RoomRequestDto dto
            , HttpServletRequest httpServletRequest) throws NoAuthorizationTokenException, UserNotFoundException,
            MethodArgumentNotValidException, RoomNotFoundException;
}
