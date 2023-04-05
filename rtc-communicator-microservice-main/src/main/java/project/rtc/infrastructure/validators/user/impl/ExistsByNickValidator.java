package project.rtc.infrastructure.validators.user.impl;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import lombok.RequiredArgsConstructor;

import project.rtc.communicator.user.repositories.UserRepository;
import project.rtc.infrastructure.validators.user.ExistsByNick;

@RequiredArgsConstructor
public class ExistsByNickValidator implements ConstraintValidator<ExistsByNick, String> {
	
	private final UserRepository userRepository;

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		if(userRepository.existsByNick(value))
			return false;
		return true;
	}
}
