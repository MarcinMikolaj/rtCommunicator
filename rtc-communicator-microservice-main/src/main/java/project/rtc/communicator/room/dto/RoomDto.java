package project.rtc.communicator.room.dto;

import lombok.*;
import project.rtc.communicator.messager.entities.Message;
import project.rtc.communicator.user.entities.User;

import javax.persistence.ElementCollection;
import java.util.List;

@AllArgsConstructor
@Getter
@ToString
public class RoomDto {
    private String roomId;
    private String name;

    // Last message sent in the room.
    private Message lastMessage;
    @ElementCollection
    @ToString.Exclude
    private List<User> users;
}
