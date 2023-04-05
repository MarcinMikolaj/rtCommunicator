package project.rtc.infrastructure.validators.room.impl;

import lombok.RequiredArgsConstructor;
import project.rtc.communicator.room.dto.RoomRequestDto;
import project.rtc.communicator.room.repositories.RoomRepository;
import project.rtc.infrastructure.validators.room.IsEqualsRoomName;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@RequiredArgsConstructor
public class IsEqualsRoomNameValidator implements ConstraintValidator<IsEqualsRoomName, RoomRequestDto> {

    private final RoomRepository roomRepository;

    @Override
    public void initialize(IsEqualsRoomName constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(RoomRequestDto value, ConstraintValidatorContext context) {
        return roomRepository.findByRoomId(value.getRoomId()).get().getName().equals(value.getRoomName());
    }
}
