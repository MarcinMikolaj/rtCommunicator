package project.rtc.authorization.basic_login.controllers.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import project.rtc.authorization.basic_login.controllers.AuthenticationRestController;
import project.rtc.authorization.basic_login.controllers.dto.LoginRequestPayload;
import project.rtc.authorization.basic_login.controllers.dto.LoginResponsePayload;
import project.rtc.authorization.basic_login.controllers.dto.LogoutRequestPayload;
import project.rtc.authorization.basic_login.controllers.impl.exceptions.AuthenticationException;
import project.rtc.authorization.basic_login.services.AuthenticationService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@RequiredArgsConstructor
public class AuthenticationRestControllerImpl implements AuthenticationRestController {
    private final AuthenticationService loginService;

    @Override
    public ResponseEntity<LoginResponsePayload> authenticate(HttpServletResponse response
            , LoginRequestPayload loginRequest) throws AuthenticationException {
        return new ResponseEntity<>(loginService.authenticate(response, loginRequest), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<String> logout(LogoutRequestPayload logoutRequestPayload, HttpServletRequest request
            , HttpServletResponse response) throws IOException {
        loginService.logout(request, response, logoutRequestPayload);
        return new ResponseEntity<>("logged out", HttpStatus.OK);
    }
}
