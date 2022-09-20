package project.rtc.registration;

import javax.validation.constraints.NotBlank;

import project.rtc.registration.validators.ImgFileExtension;

public final class ProfilePicture {
	
	@NotBlank(message = "The picture cannot be blank")
	@ImgFileExtension(message = "Incorrect file extension")
	private final String name;
	private final String type;
	private final int size;
	private final String fileInBase64;
	
	public ProfilePicture(String name, String type, int size, String fileInBase64) {
		super();
		this.name = name;
		this.type = type;
		this.size = size;
		this.fileInBase64 = fileInBase64;
	}
	
	public String getName() {
		return name;
	}
	
	public String getType() {
		return type;
	}
	
	public int getSize() {
		return size;
	}
	
	public String getFileInBase64() {
		return fileInBase64;
	}

	@Override
	public String toString() {
		return "Picture [name=" + name + ", type=" + type + ", size=" + size + ", fileInBase64=" + fileInBase64 + "]";
	}
	
}
