package project.rtc.registration.controllers.impl;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.rtc.infrastructure.exception.exceptions.InvalidTokenException;
import project.rtc.registration.dto.RegistrationRequestDto;
import project.rtc.registration.services.impl.RegistrationServiceImpl;
import project.rtc.registration.controllers.RegistrationRestController;

@RestController
@RequiredArgsConstructor
public class RegistrationRestControllerImpl implements RegistrationRestController {
	
	private final RegistrationServiceImpl registrationServiceImpl;

	@CrossOrigin(origins = "*", maxAge = 3600)
	public ResponseEntity<?> register(RegistrationRequestDto registrationRequestDto) throws MessagingException {
		registrationServiceImpl.registerAccount(registrationRequestDto);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	public ResponseEntity<?> activate(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse)
			throws InvalidTokenException {
		registrationServiceImpl.activateAccount(httpServletRequest, httpServletResponse);
		return new ResponseEntity<>(HttpStatus.OK);
	}

}
