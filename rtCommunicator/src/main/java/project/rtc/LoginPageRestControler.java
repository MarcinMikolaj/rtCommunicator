package project.rtc;

import java.sql.Timestamp;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginPageRestControler {
	
//	@RequestMapping(path = "/h", method = RequestMethod.POST)
//	public ResponseEntity<String> getUserCredentials(@RequestBody Credentials credentials) {
//		
//		System.out.println(credentials.getLogin());
//		
//		if(credentials.getLogin() == "Marcin") {
//			
//			Timestamp timestamp = new Timestamp(System.currentTimeMillis());
//			HttpHeaders headers = new HttpHeaders();
//			headers.add("response_timestamp", timestamp.toString());
//			ResponseEntity<String> response = new ResponseEntity<String>("OK", headers, HttpStatus.OK);
//			return response;
//			
//		}else {
//			Timestamp timestamp = new Timestamp(System.currentTimeMillis());
//			HttpHeaders headers = new HttpHeaders();
//			headers.add("response_timestamp", timestamp.toString());
//			ResponseEntity<String> response = new ResponseEntity<String>("UNAUTHORIZED", headers, HttpStatus.UNAUTHORIZED);
//			return response;
//		}
//		
//	}

}
