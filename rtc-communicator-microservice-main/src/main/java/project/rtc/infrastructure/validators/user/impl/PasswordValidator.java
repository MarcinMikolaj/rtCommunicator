package project.rtc.infrastructure.validators.user.impl;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import project.rtc.infrastructure.validators.user.Password;

public class PasswordValidator implements ConstraintValidator<Password, String>{
	
	private boolean isEnabled;
	private static final String passwordPattern = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{8,20}$";

	@Override
	public void initialize(Password constraintAnnotation) {
		isEnabled = constraintAnnotation.isEnabled();
	}

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		if(isEnabled == false)
			return true;
		
		if(value == null)
			return false;
		
		Pattern pattern = Pattern.compile(passwordPattern);
		Matcher matcher = pattern.matcher(value);
		return matcher.matches();
	}
	
}
