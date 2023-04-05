package project.rtc.infrastructure.validators.room;

import project.rtc.infrastructure.validators.room.impl.RoomExistByRoomIdValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = RoomExistByRoomIdValidator.class)
@Target({ElementType.TYPE, ElementType.FIELD,
        ElementType.PARAMETER,
        ElementType.METHOD,
        ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface RoomExistByRoomId {

    String message() default "No room selected";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default{};
}
