package project.rtc.infrastructure.validators.room;

import project.rtc.infrastructure.validators.room.impl.UserExistByNickValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = UserExistByNickValidator.class)
@Target({ElementType.FIELD,
        ElementType.PARAMETER,
        ElementType.METHOD,
        ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface UserExistByNick {

    String message() default "This Room name is already assigned !";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default{};

}
