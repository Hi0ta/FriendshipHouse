package sky.pro.friendshiphouse.service;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.request.SendMessage;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import sky.pro.friendshiphouse.model.Friend;
import sky.pro.friendshiphouse.model.Volunteer;
import sky.pro.friendshiphouse.repository.FriendRepository;


@RequiredArgsConstructor
@Service
public class FriendService {
    private final FriendRepository friendRepository;
    private final VolunteerService volunteerService;
    private final TelegramBot telegramBot;
    private final Logger logger = LoggerFactory.getLogger(FriendService.class);

    /**
     * Метод добавляет нового друга в БД
     *
     * @param chatId     идентификатор чата
     * @param friendName имя друга
     */
    public void createFriend(Long chatId, String friendName) {
        logger.info("launching the createFriend method");
        if (friendRepository.getFriendByFriendChatId(chatId) == null) {
            Friend friend = new Friend();
            friend.setFriendChatId(chatId);
            friend.setFriendName(friendName);
            friendRepository.save(friend);
        }
    }

    /**
     * Метод позволяет изменить данные друга - добавить в БД его номер телефона
     *
     * @param chatId    идентификатор чата
     * @param numberTel номер телефона
     */
    public void changeFriend(Long chatId, String numberTel) {
        logger.info("launching the changeFriend method");
        Friend friend = friendRepository.getFriendByFriendChatId(chatId);
        friend.setFriendTelNumber(numberTel);
        friendRepository.save(friend);
    }

    /**
     * Метод уведомляет волонтера о том что друг хочет что бы ему позвонили
     *
     * @param friendChatId идентификатор чата
     */
    public void callMe(Long friendChatId) {
        logger.info("launching the callMe method");
        String telNumber = friendRepository.getFriendByFriendChatId(friendChatId).getFriendTelNumber();
        Volunteer volunteer = volunteerService.callVolunteer();
        Long volunteerChatId = volunteer.getVolunteerChatId();
        SendMessage callVolunteerMessage = new SendMessage(volunteerChatId, "Друг с номером тел: " + telNumber + " жаждет пообщаться, срочно свяжись с ним");
        telegramBot.execute(callVolunteerMessage);
        volunteerService.editVolunteerStatus(volunteer.getVolunteerId(), false); //после того как волонтер закончит общение с другом ему необходимо самому сменить свой статус на TRUE
    }

    /**
     * Метод уведомляет волонтера о том что друг хочет пообщаться
     *
     * @param friendChatId идентификатор чата
     */
    public void callVolunteer(Long friendChatId) {
        logger.info("launching the callVolunteer method");
        long friendId = friendRepository.getFriendByFriendChatId(friendChatId).getFriendId();
        Volunteer volunteer = volunteerService.callVolunteer();
        Long volunteerChatId = volunteer.getVolunteerChatId();
        SendMessage callVolunteerMessage = new SendMessage(volunteerChatId, "Друг (Id = " + friendId + ") жаждет пообщаться, срочно свяжись с ним");
        telegramBot.execute(callVolunteerMessage);
        volunteerService.editVolunteerStatus(volunteer.getVolunteerId(), false); //после того как волонтер закончит общение с другом ему необходимо самому сменить свой статус на TRUE
//TODO  как в сообщении этому волонтеру отправить контакт телеграмма друга с которым нужно связаться?
    }
}
