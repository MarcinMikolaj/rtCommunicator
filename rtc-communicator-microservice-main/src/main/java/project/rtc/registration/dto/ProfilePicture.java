package project.rtc.registration.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@ToString
public final class ProfilePicture {
	private final String name;
	private final String type;
	private final int size;
	private final String fileInBase64;
}
