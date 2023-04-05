package project.rtc.infrastructure.validators.user;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import project.rtc.infrastructure.validators.user.impl.ExistsByNickValidator;


//It checks if there is already an account (an instance of the User class saved in the database) with the assigned value to be checked.
@Documented
@Constraint(validatedBy = ExistsByNickValidator.class)
@Target({ElementType.FIELD,
	ElementType.PARAMETER,
	ElementType.METHOD,
	ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface ExistsByNick {
	
	String message() default "An account with the given nickname exists";
	Class<?>[] groups() default {};
	Class<? extends Payload>[] payload() default{};
	boolean isEnabled() default true;
}