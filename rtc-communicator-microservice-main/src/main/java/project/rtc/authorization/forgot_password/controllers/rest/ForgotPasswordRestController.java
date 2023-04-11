package project.rtc.authorization.forgot_password.controllers.rest;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import project.rtc.infrastructure.exception.exceptions.InvalidTokenException;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletResponse;

@RequestMapping(value = "/app/forgot", consumes = MediaType.APPLICATION_JSON_VALUE)
public interface ForgotPasswordRestController {

    @PostMapping(path = "/send")
    ResponseEntity<?> handleTheRequestToChangeThePassword(@RequestParam String email
            , HttpServletResponse response) throws MessagingException;

    @PostMapping(path = "/credentials/update")
    ResponseEntity<?> handleTheAttemptToChangeThePassword(@RequestParam String token
            , @RequestParam String password) throws InvalidTokenException;
}
