package sky.pro.friendshiphouse.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import sky.pro.friendshiphouse.model.Friend;
import sky.pro.friendshiphouse.repository.FriendRepository;

@Service
public class FriendService {
    private final FriendRepository friendRepository;
    private final Logger logger = LoggerFactory.getLogger(FriendService.class);
    public FriendService(FriendRepository friendRepository) {
        this.friendRepository = friendRepository;
    }

    public Friend createFriend() { //должен принимать все необходимые данные для заполнения от пользователя
        logger.info("launching the createFriend method");
        Friend friend = new Friend();
        friend.setFriendChatId(1234567890);
        friend.setFriendName("");
        friend.setFriendTelNumber("");

        return friendRepository.save(friend);
    }
}
