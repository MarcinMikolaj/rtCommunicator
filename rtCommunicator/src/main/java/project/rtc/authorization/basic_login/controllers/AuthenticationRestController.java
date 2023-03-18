package project.rtc.authorization.basic_login.controllers;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import project.rtc.authorization.basic_login.controllers.dto.LoginRequestPayload;
import project.rtc.authorization.basic_login.controllers.dto.LoginResponsePayload;
import project.rtc.authorization.basic_login.controllers.dto.LogoutRequestPayload;
import project.rtc.authorization.basic_login.controllers.impl.exceptions.AuthenticationException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RequestMapping(value = "/app", consumes = MediaType.APPLICATION_JSON_VALUE)
public interface AuthenticationRestController {

    @PostMapping("/login")
    ResponseEntity<LoginResponsePayload> authenticate(HttpServletResponse response
            , @RequestBody LoginRequestPayload loginRequest) throws AuthenticationException;

    @PostMapping("/logout")
    ResponseEntity<String> logout(@RequestBody LogoutRequestPayload logoutRequestPayload
            , HttpServletRequest request, HttpServletResponse response) throws IOException;
}
