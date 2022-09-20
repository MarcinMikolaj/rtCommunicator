package project.rtc.registration.validators.impl;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import project.rtc.authorization.basic_login.credentials.CredentialsRepository;
import project.rtc.registration.validators.ExistsByEmail;

public class ExistByEmailValidator implements ConstraintValidator<ExistsByEmail, String> {
	
	private CredentialsRepository credentialsRepository;
	
	@Autowired
	public void setCredentialsRepository(CredentialsRepository credentialsRepository) {
		this.credentialsRepository = credentialsRepository;
	}

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		
		if(credentialsRepository.existByEmail(value))
			return false;
		
		return true;
	}

}
