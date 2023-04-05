package project.rtc.infrastructure.validators.room.impl;

import lombok.RequiredArgsConstructor;
import project.rtc.communicator.room.dto.RoomRequestDto;
import project.rtc.communicator.user.repositories.UserRepository;
import project.rtc.infrastructure.validators.room.IsRoomNotContainUser;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@RequiredArgsConstructor
public class IsRoomNotContainUserValidator implements ConstraintValidator<IsRoomNotContainUser, RoomRequestDto> {

    private final UserRepository userRepository;

    @Override
    public void initialize(IsRoomNotContainUser constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(RoomRequestDto value, ConstraintValidatorContext context) {
        try{
            return !userRepository.findByNick(value.getUserNick()).get().getRoomsId().contains(value.getRoomId());
        } catch (Exception e){
            return true;
        }
    }

}
