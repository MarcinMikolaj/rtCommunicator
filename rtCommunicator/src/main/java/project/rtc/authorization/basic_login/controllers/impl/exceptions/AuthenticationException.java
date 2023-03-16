package project.rtc.authorization.basic_login.controllers.impl.exceptions;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class AuthenticationException extends Exception {

    public AuthenticationException(String message){
        super(message);
    }

}
