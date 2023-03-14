package project.rtc.registration.validators.impl;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import project.rtc.communicator.user.repositories.UserRepository;
import project.rtc.registration.validators.ExistsByNick;


public class ExistsByNickValidator implements ConstraintValidator<ExistsByNick, String> {
	
	private UserRepository userRepository;
	
	@Autowired
	public void setUserRepository(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		
		if(userRepository.existsByNick(value))
			return false;
		
		return true;
	}

}
