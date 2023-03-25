package project.rtc.infrastructure.validators.room;

import project.rtc.infrastructure.validators.room.impl.IsRoomNameNotAssignedValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = IsRoomNameNotAssignedValidator.class)
@Target({ElementType.FIELD,
        ElementType.PARAMETER,
        ElementType.METHOD,
        ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface IsRoomNameNotAssigned {

    String message() default "This Room name is already assigned !";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default{};
}
