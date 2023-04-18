package project.rtc.infrastructure.validators.user.impl;

import project.rtc.infrastructure.validators.user.Nick;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NickValidator implements ConstraintValidator<Nick, String> {

    private static final String nickPattern = "^[a-zA-Z0-9_]{4,15}$";

    @Override
    public void initialize(Nick constraintAnnotation) {ConstraintValidator.super.initialize(constraintAnnotation);}

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        Pattern pattern = Pattern.compile(nickPattern);
        Matcher matcher = pattern.matcher(value);
        return matcher.matches();
    }
}
