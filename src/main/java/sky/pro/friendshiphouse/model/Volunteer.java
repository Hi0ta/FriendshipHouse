package sky.pro.friendshiphouse.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
//@Table(name = "volunteer")
public class Volunteer {

    @Id
    @GeneratedValue
    private long volunteerId;
    private Long volunteerChatId;
    private String volunteerName;
    private boolean volunteerStatusFree; //занят; свободен

}
