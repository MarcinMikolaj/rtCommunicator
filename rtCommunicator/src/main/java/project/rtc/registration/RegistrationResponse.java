package project.rtc.registration;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@ToString
public class RegistrationResponse {
	
	private boolean isSuccessful;
	private String successfulMessage;
	private List<ErrorMessage> errorMessages;

}
