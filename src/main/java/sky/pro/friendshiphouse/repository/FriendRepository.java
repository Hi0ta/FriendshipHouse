package sky.pro.friendshiphouse.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sky.pro.friendshiphouse.model.Friend;


@Repository
public interface FriendRepository extends JpaRepository<Friend, Long> {
    Friend getFriendByFriendChatId(long friendChatId);
}
