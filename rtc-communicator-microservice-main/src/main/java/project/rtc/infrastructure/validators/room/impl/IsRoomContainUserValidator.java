package project.rtc.infrastructure.validators.room.impl;

import lombok.RequiredArgsConstructor;
import project.rtc.communicator.room.dto.RoomRequestDto;
import project.rtc.communicator.user.repositories.UserRepository;
import project.rtc.infrastructure.validators.room.IsRoomContainUser;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@RequiredArgsConstructor
public class IsRoomContainUserValidator implements ConstraintValidator<IsRoomContainUser, RoomRequestDto> {

    private final UserRepository userRepository;

    @Override
    public void initialize(IsRoomContainUser constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(RoomRequestDto value, ConstraintValidatorContext context) {
        try{
            return userRepository.findByNick(value.getUserNick()).get().getRoomsId().contains(value.getRoomId());
        } catch (Exception e){
            return false;
        }
    }

}
