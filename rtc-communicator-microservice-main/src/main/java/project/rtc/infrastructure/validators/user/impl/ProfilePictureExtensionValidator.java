package project.rtc.infrastructure.validators.user.impl;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import project.rtc.infrastructure.validators.user.ProfilePictureExtension;
import project.rtc.registration.dto.ProfilePicture;

public class ProfilePictureExtensionValidator implements ConstraintValidator<ProfilePictureExtension, ProfilePicture> {
	private static final String fileExtensionPattern = "([^\\s]+(\\.(?i)(jpe?g|png|bin|gif|bmp))$)";
	@Override
	public boolean isValid(ProfilePicture value, ConstraintValidatorContext context) {
		Pattern pattern = Pattern.compile(fileExtensionPattern);
		Matcher matcher = pattern.matcher(value.getName());
		return matcher.matches();
	}
}
