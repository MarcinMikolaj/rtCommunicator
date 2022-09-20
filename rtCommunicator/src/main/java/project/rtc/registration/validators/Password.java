package project.rtc.registration.validators;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import project.rtc.registration.validators.impl.PasswordValidator;


// Password must contain at least one digit [0-9].
// Password must contain at least one lowercase Latin character [a-z].
// Password must contain at least one uppercase Latin character [A-Z].
// Password must contain at least one special character like ! @ # & ( ).
// Password must contain a length of at least 8 characters and a maximum of 20 characters.
@Documented
@Constraint(validatedBy = PasswordValidator.class)
@Target({ElementType.FIELD,
	ElementType.PARAMETER,
	ElementType.METHOD,
	ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface Password {
	
	String message() default "The password is incorrect";
	Class<?>[] groups() default {};
	Class<? extends Payload>[] payload() default{};
	boolean isEnabled() default true;
}
