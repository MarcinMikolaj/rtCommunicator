package project.rtc.registration;

import lombok.Data;

@Data
public final class ErrorMessage {
	
	private final String field;
	private final String message;

}
