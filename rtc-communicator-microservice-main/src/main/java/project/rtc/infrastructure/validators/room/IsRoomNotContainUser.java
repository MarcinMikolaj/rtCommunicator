package project.rtc.infrastructure.validators.room;

import project.rtc.infrastructure.validators.room.impl.IsRoomNotContainUserValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = IsRoomNotContainUserValidator.class)
@Target({ElementType.TYPE, ElementType.FIELD,
        ElementType.PARAMETER,
        ElementType.METHOD,
        ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface IsRoomNotContainUser {

    String message() default "Room already contain this user !";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default{};
}
