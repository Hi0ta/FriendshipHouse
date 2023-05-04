package sky.pro.friendshiphouse.model;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class Volunteer {

    @Id
    @GeneratedValue
    private long volunteerId;
    private Integer volunteerChatId;
    private String volunteerName;
    private boolean volunteerStatusFree; //занят; свободен

    public long getVolunteerId() {
        return volunteerId;
    }

    public void setVolunteerId(long volunteerId) {
        this.volunteerId = volunteerId;
    }

    public String getVolunteerName() {
        return volunteerName;
    }

    public void setVolunteerName(String volunteerName) {
        this.volunteerName = volunteerName;
    }

    public boolean isVolunteerStatusFree() {
        return volunteerStatusFree;
    }

    public void setVolunteerStatusFree(boolean volunteerStatusFree) {
        this.volunteerStatusFree = volunteerStatusFree;
    }

    public Integer getVolunteerChatId() {
        return volunteerChatId;
    }

    public void setVolunteerChatId(Integer volunteerChatId) {
        this.volunteerChatId = volunteerChatId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Volunteer volunteer = (Volunteer) o;
        return volunteerId == volunteer.volunteerId && volunteerStatusFree == volunteer.volunteerStatusFree && Objects.equals(volunteerChatId, volunteer.volunteerChatId) && Objects.equals(volunteerName, volunteer.volunteerName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(volunteerId, volunteerChatId, volunteerName, volunteerStatusFree);
    }

    @Override
    public String toString() {
        return "Volunteer{" +
                "volunteerId=" + volunteerId +
                ", volunteerName='" + volunteerName + '\'' +
                ", volunteerStatus=" + volunteerStatusFree +
                ", volunteerChatId='" + volunteerChatId + '\'' +
                '}';
    }
}
