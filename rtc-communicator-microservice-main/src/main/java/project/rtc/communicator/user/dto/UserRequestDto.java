package project.rtc.communicator.user.dto;

import lombok.Data;
import project.rtc.infrastructure.groups.user.UpdateUserEmailGroups;
import project.rtc.infrastructure.groups.user.UpdateUserNickGroup;
import project.rtc.infrastructure.groups.user.UpdateUserPasswordGroup;
import project.rtc.infrastructure.validators.user.ExistsByEmail;
import project.rtc.infrastructure.validators.user.ExistsByNick;
import project.rtc.infrastructure.validators.user.Password;
import project.rtc.registration.dto.ProfilePicture;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
public class UserRequestDto {

    private String userId;
    @Password(groups = {UpdateUserPasswordGroup.class}, message = "The specified password is incorrect")
    private String password;

    @NotBlank(groups = {UpdateUserEmailGroups.class}, message = "Email address cannot be empty")
    @Email(groups = {UpdateUserEmailGroups.class}, message = "The email address must be in the correct format")
    @ExistsByEmail(groups = {UpdateUserEmailGroups.class}, message = "An account with the given e-mail address already exists")
    private String email;

    @NotBlank(groups = {UpdateUserNickGroup.class}, message = "Nick cannot be empty")
    @Pattern(groups = {UpdateUserNickGroup.class}, regexp = "^[a-zA-Z0-9]*$", message = "Nick can only contain letters and numbers")
    @Size(groups = {UpdateUserNickGroup.class}, max = 15, message = "Nickname can be up to 15 characters long")
    @ExistsByNick(groups = {UpdateUserNickGroup.class}, message = "An account with the given nickname exists")
    private String nick;
    private ProfilePicture profilePicture;

}
