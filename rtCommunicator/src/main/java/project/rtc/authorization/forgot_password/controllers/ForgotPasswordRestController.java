package project.rtc.authorization.forgot_password.controllers;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import project.rtc.authorization.forgot_password.ForgotPasswordRequest;
import project.rtc.authorization.forgot_password.reset_password_token.ChangePasswordRequest;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RequestMapping(value = "/app/forgot", consumes = MediaType.APPLICATION_JSON_VALUE)
public interface ForgotPasswordRestController {

    @PostMapping(path = "/send")
    ResponseEntity<String> handleTheRequestToChangeThePassword(@RequestBody ForgotPasswordRequest forgotPasswordRequest
            , HttpServletResponse response) throws IOException;

    @PostMapping(path = "/credentials/update")
    ResponseEntity<?> handleTheAttemptToChangeThePassword(@RequestBody ChangePasswordRequest changePasswordRequest);

}
