package project.rtc.infrastructure.exception.exceptions;

import lombok.NoArgsConstructor;
@NoArgsConstructor
public class RoomNotFoundException extends Exception{

    public RoomNotFoundException(String message){super("Room not found.");};

}
