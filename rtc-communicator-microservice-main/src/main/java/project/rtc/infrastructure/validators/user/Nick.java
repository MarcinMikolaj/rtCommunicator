package project.rtc.infrastructure.validators.user;

import project.rtc.infrastructure.validators.user.impl.NickValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

// Can only contain letters and numbers.
// Must contain between 4-30 characters.
@Documented
@Constraint(validatedBy = NickValidator.class)
@Target({ElementType.FIELD,
        ElementType.PARAMETER,
        ElementType.METHOD,
        ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface Nick {
    String message() default "Nickname does not meet the conditions";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default{};
}
