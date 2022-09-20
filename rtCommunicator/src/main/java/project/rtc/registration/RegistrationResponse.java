package project.rtc.registration;

import java.util.List;

public class RegistrationResponse {
	
	private boolean isSuccessful;
	private String successfulMessage;
	private List<ErrorMessage> errorMessages;
	
	
	public RegistrationResponse() {
		super();
	}

	public RegistrationResponse(boolean isSuccessful, String successfulMessage, List<ErrorMessage> errorMessages) {
		super();
		this.isSuccessful = isSuccessful;
		this.successfulMessage = successfulMessage;
		this.errorMessages = errorMessages;
	}


	public boolean isSuccessful() {
		return isSuccessful;
	}


	public void setSuccessful(boolean isSuccessful) {
		this.isSuccessful = isSuccessful;
	}


	public List<ErrorMessage> getErrorMessages() {
		return errorMessages;
	}


	public void setErrorMessages(List<ErrorMessage> errorMessages) {
		this.errorMessages = errorMessages;
	}
	
	
	public String getSuccessfulMessage() {
		return successfulMessage;
	}

	public void setSuccessfulMessage(String successfulMessage) {
		this.successfulMessage = successfulMessage;
	}
	

	@Override
	public String toString() {
		return "RegistrationResponse [isSuccessful=" + isSuccessful + ", errorMessages=" + errorMessages + "]";
	}

}
