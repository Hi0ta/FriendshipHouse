package sky.pro.friendshiphouse.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
/**
 * Пользователь бота
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
//@Table(name = "friend")
public class Friend {
    @Id
    @GeneratedValue
    private long friendId;
    private Long friendChatId;
    private String friendName;
    private String friendTelNumber;
}
