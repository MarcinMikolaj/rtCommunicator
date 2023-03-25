package project.rtc.infrastructure.exception.exceptions;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class AuthenticationException extends Exception {

    public AuthenticationException(String message){
        super(message);
    }

}
