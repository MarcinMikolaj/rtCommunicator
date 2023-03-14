package project.rtc.registration.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import project.rtc.registration.dto.RegistrationRequest;
import project.rtc.registration.dto.RegistrationResponse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface RegistrationRestController {

    ResponseEntity<RegistrationResponse> register(@RequestBody RegistrationRequest registrationRequest);

    ResponseEntity<String> activate(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse);

}
