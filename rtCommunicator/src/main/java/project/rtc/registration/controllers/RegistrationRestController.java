package project.rtc.registration.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import project.rtc.registration.dto.RegistrationRequest;
import project.rtc.registration.dto.RegistrationResponse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RequestMapping(value = "/app/registration")
public interface RegistrationRestController {

    @PostMapping(path = "/create")
    ResponseEntity<RegistrationResponse> register(@RequestBody RegistrationRequest registrationRequest);

    @GetMapping(path = "/activate")
    ResponseEntity<String> activate(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse);

}
