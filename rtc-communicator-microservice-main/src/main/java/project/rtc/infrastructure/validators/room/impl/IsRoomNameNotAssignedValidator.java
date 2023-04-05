package project.rtc.infrastructure.validators.room.impl;

import lombok.RequiredArgsConstructor;
import project.rtc.communicator.room.repositories.RoomRepository;
import project.rtc.infrastructure.validators.room.IsRoomNameNotAssigned;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@RequiredArgsConstructor
public class IsRoomNameNotAssignedValidator implements ConstraintValidator<IsRoomNameNotAssigned, String> {

    private final RoomRepository roomRepository;

    @Override
    public void initialize(IsRoomNameNotAssigned constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return !roomRepository.existsByName(value);
    }
}
