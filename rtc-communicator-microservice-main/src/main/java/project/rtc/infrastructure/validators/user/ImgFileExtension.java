package project.rtc.infrastructure.validators.user;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import project.rtc.infrastructure.validators.user.impl.ImgFileExtensionValidator;

@Documented
@Constraint(validatedBy = ImgFileExtensionValidator.class)
@Target({ElementType.FIELD,
	ElementType.PARAMETER,
	ElementType.METHOD,
	ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface ImgFileExtension {
	
	String message() default "Incorrect file extension";
	Class<?>[] groups() default {};
	Class<? extends Payload>[] payload() default{};
	boolean isEnabled() default true;

}
