package project.rtc.communicator.user.dto;

import java.util.Date;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import project.rtc.communicator.user.entities.User;

@Getter
@Setter
@ToString
@Builder
public class UserResponseBody {

	private Date timestamp;
	private UserOperation operation;
	private User user;
}
