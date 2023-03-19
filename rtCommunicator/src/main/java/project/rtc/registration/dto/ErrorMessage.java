package project.rtc.registration.dto;

import lombok.Data;

@Data
public final class ErrorMessage {
	
	private final String field;
	private final String message;

}
