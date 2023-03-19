package project.rtc.registration.validators.impl;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import project.rtc.authorization.basic_login.credentials.repositories.CredentialsRepository;
import project.rtc.authorization.basic_login.credentials.repositories.impl.CredentialsRepositoryImpl;
import project.rtc.registration.validators.ExistsByEmail;

public class ExistByEmailValidator implements ConstraintValidator<ExistsByEmail, String> {
	
	private CredentialsRepository credentialsRepository;
	
	public ExistByEmailValidator(CredentialsRepositoryImpl credentialsRepositoryImpl) {
		this.credentialsRepository = credentialsRepositoryImpl;
	}
	

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		
		if(credentialsRepository.existByEmail(value))
			return false;
		
		return true;
	}

}
