package project.rtc.infrastructure.validators.room;

import project.rtc.infrastructure.validators.room.impl.IsEqualsRoomNameValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = IsEqualsRoomNameValidator.class)
@Target({ElementType.TYPE, ElementType.FIELD,
        ElementType.PARAMETER,
        ElementType.METHOD,
        ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface IsEqualsRoomName {

    String message() default "Incorrect room name";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default{};
}
