package sky.pro.friendshiphouse.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sky.pro.friendshiphouse.model.Friend;


public interface FriendRepository extends JpaRepository<Friend, Long> {
}
