package project.rtc.authorization.basic_login.controllers.impl.exceptions;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import project.rtc.authorization.basic_login.controllers.dto.LoginResponsePayload;
import project.rtc.authorization.basic_login.controllers.impl.exceptions.AuthenticationException;

// This class allow handel AuthenticationException across the whole application
@ControllerAdvice
public class AuthorizationExceptionHandler {

    @ExceptionHandler(AuthenticationException.class)
    protected ResponseEntity<LoginResponsePayload> handleException(){

        return new ResponseEntity<LoginResponsePayload>(new LoginResponsePayload(null, false), HttpStatus.UNAUTHORIZED);
    }

}
