package project.rtc.infrastructure.validators.room;

import project.rtc.infrastructure.validators.room.impl.IsRoomContainUserValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = IsRoomContainUserValidator.class)
@Target({ElementType.TYPE, ElementType.FIELD,
        ElementType.PARAMETER,
        ElementType.METHOD,
        ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface IsRoomContainUser {

    String message() default "Room not contain this user !";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default{};

}
