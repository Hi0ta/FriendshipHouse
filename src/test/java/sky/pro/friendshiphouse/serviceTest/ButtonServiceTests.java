package sky.pro.friendshiphouse.serviceTest;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.request.SendPhoto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import sky.pro.friendshiphouse.service.ButtonService;
import sky.pro.friendshiphouse.service.FriendService;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class ButtonServiceTests {

    @InjectMocks
    private ButtonService buttonService;
    @Mock
    private TelegramBot telegramBot;
    @Mock
    private FriendService friendService;

    private final Long chatId = 123456789L;

    private String data;

    @Test
    public void testHandleDogButton() {
        data = "dogButton";
        buttonService.buttonSelection(data, chatId);
        verify(telegramBot, times(1)).execute(any(SendMessage.class));
    }
    @Test
    public void testHandleDogInfo() {
        data = "dogInfo";
        buttonService.buttonSelection(data, chatId);
        verify(telegramBot, times(1)).execute(any(SendMessage.class));
    }
    @Test
    public void testHandleDogConsult() {
        data = "dogConsult";
        buttonService.buttonSelection(data, chatId);
        verify(telegramBot, times(1)).execute(any(SendMessage.class));
    }

    @Test
    public void testHandleReport() {
        data = "report";
        buttonService.buttonSelection(data, chatId);
        verify(telegramBot, times(1)).execute(any(SendMessage.class));
    }
    @Test
    public void testHandleDogAddress() {
        data = "dogAddress";
        buttonService.buttonSelection(data, chatId);
        verify(telegramBot, times(1)).execute(any(SendMessage.class));
        verify(telegramBot, times(1)).execute(any(SendPhoto.class));
    }
    @Test
    public void testHandleDogRecommendations() {
        data = "dogRecommendations";
        buttonService.buttonSelection(data, chatId);
        verify(telegramBot, times(1)).execute(any(SendMessage.class));
    }

    @Test
    public void testHandleDogIntroduction() {
        data = "dogIntroduction";
        buttonService.buttonSelection(data, chatId);
        verify(telegramBot, times(1)).execute(any(SendMessage.class));
    }

    @Test
    public void testHandleDogDocList() {
        data = "dogDocList";
        buttonService.buttonSelection(data, chatId);
        verify(telegramBot, times(1)).execute(any(SendMessage.class));
    }
    @Test
    public void testHandleDogTransportation() {
        data = "dogTransportation";
        buttonService.buttonSelection(data, chatId);
        verify(telegramBot, times(1)).execute(any(SendMessage.class));
    }

    @Test
    public void testHandleDogHomeImprovement() {
        data = "dogHomeImprovement";
        buttonService.buttonSelection(data, chatId);
        verify(telegramBot, times(1)).execute(any(SendMessage.class));
    }

    @Test
    public void testHandleHomePuppy() {
        data = "homePuppy";
        buttonService.buttonSelection(data, chatId);
        verify(telegramBot, times(1)).execute(any(SendMessage.class));
    }

    @Test
    public void testHandleHomeDog() {
        data = "homeDog";
        buttonService.buttonSelection(data, chatId);
        verify(telegramBot, times(1)).execute(any(SendMessage.class));
    }
    @Test
    public void testHandleHomeDogWithoutVision() {
        data = "homeDogWithoutVision";
        buttonService.buttonSelection(data, chatId);
        verify(telegramBot, times(1)).execute(any(SendMessage.class));
    }
    @Test
    public void testHandleHomeDogWithoutMovement() {
        data = "homeDogWithoutMovement";
        buttonService.buttonSelection(data, chatId);
        verify(telegramBot, times(1)).execute(any(SendMessage.class));
    }
    @Test
    public void testHandleAdvice() {
        data = "advice";
        buttonService.buttonSelection(data, chatId);
        verify(telegramBot, times(1)).execute(any(SendMessage.class));
    }

    @Test
    public void testHandleCynologistList() {
        data = "cynologistList";
        buttonService.buttonSelection(data, chatId);
        verify(telegramBot, times(1)).execute(any(SendMessage.class));
    }
    @Test
    public void testHandleDogReasonRefusal() {
        data = "dogReasonRefusal";
        buttonService.buttonSelection(data, chatId);
        verify(telegramBot, times(1)).execute(any(SendMessage.class));
    }
    @Test
    public void testHandleCatButton() {
        data = "catButton";
        buttonService.buttonSelection(data, chatId);
        verify(telegramBot, times(1)).execute(any(SendMessage.class));
    }
    @Test
    public void testHandleCatInfo() {
        data = "catInfo";
        buttonService.buttonSelection(data, chatId);
        verify(telegramBot, times(1)).execute(any(SendMessage.class));
    }

    @Test
    public void testHandleCatConsult() {
        data = "catConsult";
        buttonService.buttonSelection(data, chatId);
        verify(telegramBot, times(1)).execute(any(SendMessage.class));
    }
    @Test
    public void testHandleCatAddress() {
        data = "catAddress";
        buttonService.buttonSelection(data, chatId);
        verify(telegramBot, times(1)).execute(any(SendMessage.class));
        verify(telegramBot, times(1)).execute(any(SendPhoto.class));
    }

    @Test
    public void testHandleCatRecommendations() {
        data = "catRecommendations";
        buttonService.buttonSelection(data, chatId);
        verify(telegramBot, times(1)).execute(any(SendMessage.class));
    }
    @Test
    public void testHandleCatIntroduction() {
        data = "catIntroduction";
        buttonService.buttonSelection(data, chatId);
        verify(telegramBot, times(1)).execute(any(SendMessage.class));
    }

    @Test
    public void testHandleCatDocList() {
        data = "catDocList";
        buttonService.buttonSelection(data, chatId);
        verify(telegramBot, times(1)).execute(any(SendMessage.class));
    }

    @Test
    public void testHandleCatTransportation() {
        data = "catTransportation";
        buttonService.buttonSelection(data, chatId);
        verify(telegramBot, times(1)).execute(any(SendMessage.class));
    }

    @Test
    public void testHandleCatHomeImprovement() {
        data = "catHomeImprovement";
        buttonService.buttonSelection(data, chatId);
        verify(telegramBot, times(1)).execute(any(SendMessage.class));
    }

    @Test
    public void testHandleHomeKitty() {
        data = "homeKitty";
        buttonService.buttonSelection(data, chatId);
        verify(telegramBot, times(1)).execute(any(SendMessage.class));
    }

    @Test
    public void testHandleHomeCat() {
        data = "homeCat";
        buttonService.buttonSelection(data, chatId);
        verify(telegramBot, times(1)).execute(any(SendMessage.class));
    }

    @Test
    public void testHandleHomeCatWithoutVision() {
        data = "homeCatWithoutVision";
        buttonService.buttonSelection(data, chatId);
        verify(telegramBot, times(1)).execute(any(SendMessage.class));
    }
    @Test
    public void testHandleHomeCatWithoutMovement() {
        data = "homeCatWithoutMovement";
        buttonService.buttonSelection(data, chatId);
        verify(telegramBot, times(1)).execute(any(SendMessage.class));
    }
    @Test
    public void testHandleCatReasonRefusal() {
        data = "catReasonRefusal";
        buttonService.buttonSelection(data, chatId);
        verify(telegramBot, times(1)).execute(any(SendMessage.class));
    }
    @Test
    public void testHandleCallVolunteer() {
        data = "callVolunteer";
        buttonService.buttonSelection(data, chatId);
        verify(telegramBot, times(1)).execute(any(SendMessage.class));
    }
    @Test
    public void testHandleCallMeBack() {
        data = "callMeBack";
        buttonService.buttonSelection(data, chatId);
        verify(telegramBot, times(1)).execute(any(SendMessage.class));
    }
}
