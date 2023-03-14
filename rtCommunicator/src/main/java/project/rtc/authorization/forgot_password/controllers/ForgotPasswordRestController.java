package project.rtc.authorization.forgot_password.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import project.rtc.authorization.forgot_password.ForgotPasswordRequest;
import project.rtc.authorization.forgot_password.reset_password_token.ChangePasswordRequest;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface ForgotPasswordRestController {

    ResponseEntity<String> handleTheRequestToChangeThePassword(@RequestBody ForgotPasswordRequest forgotPasswordRequest, HttpServletResponse response) throws IOException;

    ResponseEntity<?> handleTheAttemptToChangeThePassword(@RequestBody ChangePasswordRequest changePasswordRequest);

}
