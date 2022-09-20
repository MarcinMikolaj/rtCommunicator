package project.rtc.registration;

public final class ErrorMessage {
	
	private final String field;
	private final String message;
	
	public ErrorMessage(String field, String message) {
		super();
		this.field = field;
		this.message = message;
	}

	public String getField() {
		return field;
	}

	public String getMessage() {
		return message;
	}

	
	@Override
	public String toString() {
		return "ErrorMessage [field=" + field + ", message=" + message + "]";
	}
	
}
