package project.rtc.authorization.basic_login.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import project.rtc.authorization.basic_login.controllers.dto.LoginRequestPayload;
import project.rtc.authorization.basic_login.controllers.dto.LoginResponsePayload;
import project.rtc.authorization.basic_login.controllers.dto.LogoutRequestPayload;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface AuthenticationRestController {

    ResponseEntity<LoginResponsePayload> authenticate(HttpServletResponse response, @RequestBody LoginRequestPayload loginRequest);

    ResponseEntity<String> logout(@RequestBody LogoutRequestPayload logoutRequestPayload, HttpServletRequest request, HttpServletResponse response) throws IOException;
}
