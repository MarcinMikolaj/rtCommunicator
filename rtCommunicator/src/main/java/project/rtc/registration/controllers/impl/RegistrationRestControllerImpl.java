package project.rtc.registration.controllers.impl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.rtc.registration.dto.RegistrationRequest;
import project.rtc.registration.dto.RegistrationResponse;
import project.rtc.registration.services.RegistrationService;
import project.rtc.registration.controllers.RegistrationRestController;

@RestController
@RequiredArgsConstructor
public class RegistrationRestControllerImpl implements RegistrationRestController {
	
	private final RegistrationService registrationService;

	@CrossOrigin(origins = "*", maxAge = 3600)
	@PostMapping(path = "/app/registration/create")
	public ResponseEntity<RegistrationResponse> register(@RequestBody RegistrationRequest registrationRequest){
		
		RegistrationResponse registrationResponse = registrationService.registerAccount(registrationRequest);
		
		if(registrationResponse.isSuccessful())
			return new ResponseEntity<RegistrationResponse>(registrationResponse, HttpStatus.CREATED);
		else
			return new ResponseEntity<RegistrationResponse>(registrationResponse, HttpStatus.CONFLICT);
	}
	
	@GetMapping(path = "/app/registration/activate")
	public ResponseEntity<String> activate(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
		
		boolean result = registrationService.activateAccount(httpServletRequest, httpServletResponse);
		
		if(result)
			return new ResponseEntity<String>("", HttpStatus.OK);
		else 
			return new ResponseEntity<String>("", HttpStatus.INTERNAL_SERVER_ERROR);
	}

}
