package project.rtc.registration.validators.impl;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import project.rtc.registration.validators.ImgFileExtension;

public class ImgFileExtensionValidator implements ConstraintValidator<ImgFileExtension, String> {
	
	private static final String fileExtensionPattern = "([^\\s]+(\\.(?i)(jpe?g|png|bin|gif|bmp))$)";

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		
		Pattern pattern = Pattern.compile(fileExtensionPattern);
		Matcher matcher = pattern.matcher(value);
		
		return matcher.matches();
	}

}
