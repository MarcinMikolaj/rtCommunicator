package project.rtc.registration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RegistrationRestController {
	
	private RegistrationService registrationService;
	
	@Autowired
	public void setRegistrationService(RegistrationService registrationService) {
		this.registrationService = registrationService;
	}
	
	@CrossOrigin(origins = "*", maxAge = 3600)
	@RequestMapping(path = "/app/registration/create", method = RequestMethod.POST)
	public ResponseEntity<RegistrationResponse> register(@RequestBody RegistrationRequest registrationRequest){
		
		RegistrationResponse registrationResponse = registrationService.registerAccount(registrationRequest);
		
		if(registrationResponse.isSuccessful())
			return new ResponseEntity<RegistrationResponse>(registrationResponse, HttpStatus.CREATED);
		else
			return new ResponseEntity<RegistrationResponse>(registrationResponse, HttpStatus.CONFLICT);
	}
	
	@RequestMapping(path = "/app/registration/activate", method = RequestMethod.GET)
	public ResponseEntity<String> activate(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
		
		boolean result = registrationService.activateAccount(httpServletRequest, httpServletResponse);
		
		if(result)
			return new ResponseEntity<String>("", HttpStatus.OK);
		else 
			return new ResponseEntity<String>("", HttpStatus.INTERNAL_SERVER_ERROR);
	}

}
