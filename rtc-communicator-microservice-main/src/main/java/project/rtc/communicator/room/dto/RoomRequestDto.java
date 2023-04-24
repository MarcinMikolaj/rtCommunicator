package project.rtc.communicator.room.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import project.rtc.infrastructure.groups.*;
import project.rtc.infrastructure.validators.room.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@IsRoomNotContainUser(groups = {AddUserToRoomGroup.class})
@IsRoomContainUser(groups = {RemoveUserFromRoomGroup.class})
@IsEqualsRoomName(groups = {RemoveRoomGroup.class})
public class RoomRequestDto {

	private String userId;
	private RoomOperation action;
	@NotBlank(groups = {RenameRoomNameGroup.class, AddUserToRoomGroup.class, RemoveRoomGroup.class,
			RemoveUserFromRoomGroup.class, LeaveRoomGroups.class}, message = "No room selected")
	private String roomId;
	@NotBlank(groups = {}, message = "No user name selected")
	@UserExistByNick(groups = {AddUserToRoomGroup.class}, message = "User dont exist")
	private String userNick;
	private String changedUserId;
	@Size(groups = {RenameRoomNameGroup.class, CreateRoomGroup.class}, max = 20, min = 3, message = "Allowable name length 3-20 character.")
	@IsRoomNameNotAssigned(groups = {RenameRoomNameGroup.class, CreateRoomGroup.class}, message = "Room name is already assigned !")
	private String roomName;
}
