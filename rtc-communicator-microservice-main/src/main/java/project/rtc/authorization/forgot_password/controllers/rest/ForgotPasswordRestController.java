package project.rtc.authorization.forgot_password.controllers.rest;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import project.rtc.infrastructure.exception.exceptions.InvalidTokenException;
import project.rtc.infrastructure.validators.user.Password;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletResponse;

@RequestMapping(value = "/app/forgot/api", consumes = MediaType.APPLICATION_JSON_VALUE)
@Validated
public interface ForgotPasswordRestController {

    @PostMapping(path = "/initialize")
    ResponseEntity<?> handleTheRequestToChangeThePassword(@RequestParam String email
            , HttpServletResponse response) throws MessagingException;

    @PostMapping(path = "/execute")
    ResponseEntity<?> handleTheAttemptToChangeThePassword(@RequestParam String token
            , @RequestParam @Password String password) throws InvalidTokenException;
}
