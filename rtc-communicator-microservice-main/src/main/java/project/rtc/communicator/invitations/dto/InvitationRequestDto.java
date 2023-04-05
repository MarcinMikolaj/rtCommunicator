package project.rtc.communicator.invitations.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class InvitationRequestDto {

	@NotBlank()
	private String invitationId;
}
