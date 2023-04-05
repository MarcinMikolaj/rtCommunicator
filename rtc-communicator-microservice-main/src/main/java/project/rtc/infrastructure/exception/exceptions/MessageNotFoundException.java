package project.rtc.infrastructure.exception.exceptions;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class MessageNotFoundException extends Exception{
    public MessageNotFoundException(String message){
        super("Message not found !");
    }
}
