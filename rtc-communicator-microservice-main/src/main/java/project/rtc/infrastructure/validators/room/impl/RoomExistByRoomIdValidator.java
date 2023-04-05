package project.rtc.infrastructure.validators.room.impl;

import lombok.RequiredArgsConstructor;
import project.rtc.communicator.room.repositories.RoomRepository;
import project.rtc.infrastructure.validators.room.RoomExistByRoomId;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@RequiredArgsConstructor
public class RoomExistByRoomIdValidator implements ConstraintValidator<RoomExistByRoomId, String> {

    private final RoomRepository roomRepository;

    @Override
    public void initialize(RoomExistByRoomId constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return roomRepository.existsByRoomId(value);
    }
}
