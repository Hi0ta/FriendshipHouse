package sky.pro.friendshiphouse.model;

import lombok.*;

import javax.persistence.*;
import java.util.Objects;

/**
 * Пользователь бота
 */
@Entity
@Getter
@Setter
public class Friend {
    @Id
    @GeneratedValue
    private long friendId;
    private Long friendChatId;
    private String friendName;
    private String friendTelNumber;

    public Friend() {}
    public Friend(long friendId, Long friendChatId, String friendName, String friendTelNumber) {
        this.friendId = friendId;
        this.friendChatId = friendChatId;
        this.friendName = friendName;
        this.friendTelNumber = friendTelNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Friend friend = (Friend) o;
        return friendId == friend.friendId && friendChatId.equals(friend.friendChatId) && friendName.equals(friend.friendName) && friendTelNumber.equals(friend.friendTelNumber);
    }
    @Override
    public int hashCode() {return Objects.hash(friendId, friendChatId, friendName, friendTelNumber);}

    @Override
    public String toString() {
        return "Друг " +
                "Id: " + friendId +
                ", ChatId: " + friendChatId +
                ", Имя: " + friendName +
                ", Телефон: " + friendTelNumber;
    }
}
