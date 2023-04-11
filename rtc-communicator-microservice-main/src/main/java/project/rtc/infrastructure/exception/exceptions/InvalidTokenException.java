package project.rtc.infrastructure.exception.exceptions;

public class InvalidTokenException extends Exception {
    public InvalidTokenException(String message){
        super("Invitation Token !");
    }
}
