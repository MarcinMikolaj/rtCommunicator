package project.rtc.registration.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import project.rtc.registration.dto.ErrorMessage;

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
