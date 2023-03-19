package project.rtc.registration.dto;

import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import project.rtc.registration.validators.ImgFileExtension;

@AllArgsConstructor
@Getter
@ToString
public final class ProfilePicture {
	
	@NotBlank(message = "The picture cannot be blank")
	@ImgFileExtension(message = "Incorrect file extension")
	private final String name;
	private final String type;
	private final int size;
	private final String fileInBase64;

}
