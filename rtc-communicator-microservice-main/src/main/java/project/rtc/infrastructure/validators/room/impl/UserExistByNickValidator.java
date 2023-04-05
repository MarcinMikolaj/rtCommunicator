package project.rtc.infrastructure.validators.room.impl;

import lombok.RequiredArgsConstructor;
import project.rtc.communicator.user.repositories.UserRepository;
import project.rtc.infrastructure.validators.room.UserExistByNick;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@RequiredArgsConstructor
public class UserExistByNickValidator implements ConstraintValidator<UserExistByNick, String> {

    private final UserRepository userRepository;

    @Override
    public void initialize(UserExistByNick constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return userRepository.existsByNick(value);
    }
}
