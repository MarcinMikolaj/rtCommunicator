package project.rtc.communicator.user.dto;

import java.util.Date;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import project.rtc.communicator.user.entities.User;
import project.rtc.communicator.user.entities.UserOperation;

@Getter
@Setter
@ToString
@Builder
public class UserResponseDto {
	private Date timestamp;
	private int status;
	private UserOperation operation;
	private User user;
}
