package project.rtc.authorization.basic_login.controllers.rest.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import project.rtc.authorization.basic_login.controllers.rest.AuthenticationRestController;
import project.rtc.authorization.basic_login.controllers.dto.LoginRequestPayload;
import project.rtc.infrastructure.exception.exceptions.AuthenticationException;
import project.rtc.authorization.basic_login.services.AuthenticationService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@RequiredArgsConstructor
public class AuthenticationRestControllerImpl implements AuthenticationRestController {
    private final AuthenticationService loginService;

    @Override
    public ResponseEntity<?> authenticate(HttpServletResponse response
            , LoginRequestPayload loginRequest) throws AuthenticationException {
        loginService.authenticate(response, loginRequest);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> logout(HttpServletRequest request, HttpServletResponse response) throws IOException {
        loginService.logout(request, response);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
