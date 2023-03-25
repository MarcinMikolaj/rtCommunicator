package project.rtc.communicator.room.dto;

import lombok.*;
import project.rtc.communicator.messager.entities.Message;
import project.rtc.communicator.room.entities.Room;
import project.rtc.communicator.user.entities.User;

import javax.persistence.ElementCollection;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Getter
@ToString
public class RoomDto {

    private String roomId;
    private String name;
    @ElementCollection
    @ToString.Exclude
    private List<User> users = new ArrayList<>();
    @ElementCollection
    @ToString.Exclude
    private List<Message> messages = new ArrayList<>();

    public RoomDto(Room room, List<User> users, List<Message> messages){
        this.roomId = room.getRoomId();
        this.name = room.getName();
        this.users = users;
        this.messages = messages;
    }
}
