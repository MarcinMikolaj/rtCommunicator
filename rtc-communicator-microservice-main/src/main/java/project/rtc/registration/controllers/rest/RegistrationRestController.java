package project.rtc.registration.controllers.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import project.rtc.infrastructure.exception.exceptions.InvalidTokenException;
import project.rtc.registration.dto.RegistrationRequestDto;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RequestMapping(value = "/app/registration")
public interface RegistrationRestController {

    @PostMapping(path = "/create")
    ResponseEntity<?> register(@RequestBody @Valid RegistrationRequestDto registrationRequestDto)
            throws MethodArgumentNotValidException, MessagingException;

    @GetMapping(path = "/activate")
    ResponseEntity<?> activate(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse)
            throws InvalidTokenException;

}
