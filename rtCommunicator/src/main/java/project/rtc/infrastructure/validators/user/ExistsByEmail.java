package project.rtc.infrastructure.validators.user;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import project.rtc.infrastructure.validators.user.impl.ExistByEmailValidator;

// It checks if the e-mail address provided as a value has not been previously assigned to another account.
// Where account is an instance of the class Credencials stored in the database
@Documented
@Constraint(validatedBy = ExistByEmailValidator.class)
@Target({ElementType.FIELD,
	ElementType.PARAMETER,
	ElementType.METHOD,
	ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)

public @interface ExistsByEmail {
	
	String message() default "An account with the given e-mail address already exists";
	Class<?>[] groups() default {};
	Class<? extends Payload>[] payload() default{};
	boolean isEnabled() default true;
}
