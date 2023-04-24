package project.rtc.communicator.room.dto;

import java.util.List;
import java.util.Map;
import java.util.Date;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Builder
public class RoomResponseDto {
	private Date timestamp;
	private int status;
	private RoomOperation operation;
	@ToString.Exclude
	private List<RoomDto> rooms;
	@ToString.Exclude
	private Map<String, Integer> unreadMessages;
}
