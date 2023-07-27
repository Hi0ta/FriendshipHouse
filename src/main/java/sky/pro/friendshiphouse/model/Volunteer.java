package sky.pro.friendshiphouse.model;

import lombok.*;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Getter
@Setter
public class Volunteer {

    @Id
    @GeneratedValue
    private long volunteerId;
    private Long volunteerChatId;
    private String volunteerName;
    private boolean volunteerStatusFree; //занят; свободен

    public Volunteer() {}
    public Volunteer(long volunteerId, Long volunteerChatId, String volunteerName) {
        this.volunteerId = volunteerId;
        this.volunteerChatId = volunteerChatId;
        this.volunteerName = volunteerName;
        this.volunteerStatusFree = true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Volunteer volunteer = (Volunteer) o;
        return volunteerId == volunteer.volunteerId && volunteerChatId.equals(volunteer.volunteerChatId) && volunteerName.equals(volunteer.volunteerName);
    }
    @Override
    public int hashCode() {return Objects.hash(volunteerId, volunteerChatId, volunteerName);}

    @Override
    public String toString() {
        return "Волонтер " +
                "Id: " + volunteerId +
                ", ChatId: " + volunteerChatId +
                ", Имя: " + volunteerName +
                ", Статус: " + volunteerStatusFree;
    }
}
