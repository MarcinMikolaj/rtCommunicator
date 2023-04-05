package project.rtc.infrastructure.validators.user.impl;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import lombok.RequiredArgsConstructor;
import project.rtc.authorization.basic_login.credentials.repositories.CredentialsRepository;
import project.rtc.infrastructure.validators.user.ExistsByEmail;

@RequiredArgsConstructor
public class ExistByEmailValidator implements ConstraintValidator<ExistsByEmail, String> {
	
	private final CredentialsRepository credentialsRepository;

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		if(credentialsRepository.existByEmail(value))
			return false;
		return true;
	}
}
