package project.rtc.infrastructure.exception.exceptions;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class InvitationNotFoundException extends Exception{
    public InvitationNotFoundException(String message){
        super("Invitation not found!");
    }

}
