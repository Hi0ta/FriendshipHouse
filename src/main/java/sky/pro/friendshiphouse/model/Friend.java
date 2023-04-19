package sky.pro.friendshiphouse.model;

import javax.persistence.*;
import java.util.Objects;

/**
 * Пользователь бота
 */
@Entity
public class Friend {

    @Id
    @GeneratedValue
    private Long friendId;
    private Integer friendChatId;
    private String friendName;
    private String friendTelNumber;


    public Long getFriendId() {
        return friendId;
    }

    public void setFriendId(Long friendId) {
        this.friendId = friendId;
    }

    public String getFriendName() {
        return friendName;
    }

    public void setFriendName(String friendName) {
        this.friendName = friendName;
    }

    public Integer getFriendChatId() {
        return friendChatId;
    }

    public void setFriendChatId(Integer friendChatId) {
        this.friendChatId = friendChatId;
    }

    public String getFriendTelNumber() {
        return friendTelNumber;
    }

    public void setFriendTelNumber(String friendTelNumber) {
        this.friendTelNumber = friendTelNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Friend friend = (Friend) o;
        return friendId.equals(friend.friendId) && friendName.equals(friend.friendName) && friendChatId.equals(friend.friendChatId) && friendTelNumber.equals(friend.friendTelNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(friendId, friendName, friendChatId, friendTelNumber);
    }

    @Override
    public String toString() {
        return "Friend{" +
                "friendId=" + friendId +
                ", friendName='" + friendName + '\'' +
                ", friendChatId='" + friendChatId + '\'' +
                ", friendTelNumber='" + friendTelNumber + '\'' +
                '}';
    }
}
