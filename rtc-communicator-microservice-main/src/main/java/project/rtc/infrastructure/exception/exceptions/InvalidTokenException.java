package project.rtc.infrastructure.exception.exceptions;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class InvalidTokenException extends Exception {
    public InvalidTokenException(String message){super("Invalid Token !");}
}
