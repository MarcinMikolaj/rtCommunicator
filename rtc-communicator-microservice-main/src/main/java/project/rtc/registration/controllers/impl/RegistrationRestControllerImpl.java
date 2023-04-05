package project.rtc.registration.controllers.impl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import project.rtc.registration.dto.RegistrationRequestDto;
import project.rtc.registration.services.impl.RegistrationServiceImpl;
import project.rtc.registration.controllers.RegistrationRestController;

@RestController
@RequiredArgsConstructor
public class RegistrationRestControllerImpl implements RegistrationRestController {
	
	private final RegistrationServiceImpl registrationServiceImpl;

	@CrossOrigin(origins = "*", maxAge = 3600)
	public ResponseEntity<?> register(RegistrationRequestDto registrationRequestDto) throws MethodArgumentNotValidException {
		registrationServiceImpl.registerAccount(registrationRequestDto);
		return new ResponseEntity<>(HttpStatus.valueOf(200));
	}

	public ResponseEntity<String> activate(HttpServletRequest httpServletRequest,
										   HttpServletResponse httpServletResponse) {

		boolean result = registrationServiceImpl.activateAccount(httpServletRequest, httpServletResponse);

		if(result)
			return new ResponseEntity<>("", HttpStatus.OK);
		else 
			return new ResponseEntity<>("", HttpStatus.INTERNAL_SERVER_ERROR);
	}

}
