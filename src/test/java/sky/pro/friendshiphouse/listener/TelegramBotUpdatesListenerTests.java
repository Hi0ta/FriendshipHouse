package sky.pro.friendshiphouse.listener;

import com.pengrad.telegrambot.BotUtils;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.SendResponse;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TelegramBotUpdatesListenerTests {

    @Mock
    private TelegramBot telegramBot;

    @InjectMocks
    TelegramBotUpdatesListener telegramBotUpdatesListener;

    @Test
    public void handleStartTest() throws URISyntaxException, IOException {
        String json = Files.readString(Path.of(TelegramBotUpdatesListenerTests.class.getResource("update.json").toURI()));
        Update update = BotUtils.fromJson(json.replace("%text%", "/start"), Update.class);
        SendResponse sendResponse = BotUtils.fromJson(""" 
                {
                    "ok": true
                }
                """, SendResponse.class);

        String name = null;

        when(telegramBot.execute(any())).thenReturn(sendResponse);

        telegramBotUpdatesListener.process(Collections.singletonList(update));

        ArgumentCaptor<SendMessage> argumentCaptor = ArgumentCaptor.forClass(SendMessage.class);
        Mockito.verify(telegramBot, times(2)).execute(argumentCaptor.capture());

        SendMessage actual = argumentCaptor.getValue();

        Assertions.assertThat(actual.getParameters().get("chat_id"))
                .isEqualTo(update.message().chat().id());
        Assertions.assertThat(actual.getParameters().get("text"))
                .isEqualTo("Дорогой друг, " + name + ", \n" +
                        "тебя приветствует чат бот дома для животных. \uD83D\uDE00 \n" +
                        "Все что я умею содержится в меню - ознакомься. \n" +
                        "Если чего-то не найдешь всегда сможешь позвать волонтера. \n" +
                        "(это cамый нижний пункт в меню)");
    }

    @Test
    public void handleInfoTest() throws URISyntaxException, IOException {
        String json = Files.readString(Path.of(TelegramBotUpdatesListenerTests.class.getResource("update.json").toURI()));
        Update update = BotUtils.fromJson(json.replace("%text%", "/info"), Update.class);
        SendResponse sendResponse = BotUtils.fromJson(""" 
                {
                    "ok": true
                }
                """, SendResponse.class);

        when(telegramBot.execute(any())).thenReturn(sendResponse);

        telegramBotUpdatesListener.process(Collections.singletonList(update));

        ArgumentCaptor<SendMessage> argumentCaptor = ArgumentCaptor.forClass(SendMessage.class);
        Mockito.verify(telegramBot, times(2)).execute(argumentCaptor.capture());

        SendMessage actual = argumentCaptor.getValue();

        Assertions.assertThat(actual.getParameters().get("chat_id"))
                .isEqualTo(update.message().chat().id());
        Assertions.assertThat(actual.getParameters().get("text"))
                .isEqualTo("Здесь должна быть информация о приюте(его назначение и суть)");
    }

    @Test
    public void handleConsultTest() throws URISyntaxException, IOException {
        String json = Files.readString(Path.of(TelegramBotUpdatesListenerTests.class.getResource("update.json").toURI()));
        Update update = BotUtils.fromJson(json.replace("%text%", "/consult"), Update.class);
        SendResponse sendResponse = BotUtils.fromJson(""" 
                {
                    "ok": true
                }
                """, SendResponse.class);

        when(telegramBot.execute(any())).thenReturn(sendResponse);

        telegramBotUpdatesListener.process(Collections.singletonList(update));

        ArgumentCaptor<SendMessage> argumentCaptor = ArgumentCaptor.forClass(SendMessage.class);
        Mockito.verify(telegramBot, times(2)).execute(argumentCaptor.capture());

        SendMessage actual = argumentCaptor.getValue();

        Assertions.assertThat(actual.getParameters().get("chat_id"))
                .isEqualTo(update.message().chat().id());
        Assertions.assertThat(actual.getParameters().get("text"))
                .isEqualTo("Здесь должна быть информация о предоставляемых консультациях");
    }

    @Test
    public void handleReportTest() throws URISyntaxException, IOException {
        String json = Files.readString(Path.of(TelegramBotUpdatesListenerTests.class.getResource("update.json").toURI()));
        Update update = BotUtils.fromJson(json.replace("%text%", "/report"), Update.class);
        SendResponse sendResponse = BotUtils.fromJson(""" 
                {
                    "ok": true
                }
                """, SendResponse.class);

        when(telegramBot.execute(any())).thenReturn(sendResponse);

        telegramBotUpdatesListener.process(Collections.singletonList(update));

        ArgumentCaptor<SendMessage> argumentCaptor = ArgumentCaptor.forClass(SendMessage.class);
        Mockito.verify(telegramBot, times(2)).execute(argumentCaptor.capture());

        SendMessage actual = argumentCaptor.getValue();

        Assertions.assertThat(actual.getParameters().get("chat_id"))
                .isEqualTo(update.message().chat().id());
        Assertions.assertThat(actual.getParameters().get("text"))
                .isEqualTo("Здесь должна быть информация об отчете(что он должен содержать) и предупреждение о том что он должен быть ЕЖЕДНЕВНЫМ");
    }

    @Test
    public void handleVolunteerTest() throws URISyntaxException, IOException {
        String json = Files.readString(Path.of(TelegramBotUpdatesListenerTests.class.getResource("update.json").toURI()));
        Update update = BotUtils.fromJson(json.replace("%text%", "/volunteer"), Update.class);
        SendResponse sendResponse = BotUtils.fromJson(""" 
                {
                    "ok": true
                }
                """, SendResponse.class);

        when(telegramBot.execute(any())).thenReturn(sendResponse);

        telegramBotUpdatesListener.process(Collections.singletonList(update));

        ArgumentCaptor<SendMessage> argumentCaptor = ArgumentCaptor.forClass(SendMessage.class);
        Mockito.verify(telegramBot, times(2)).execute(argumentCaptor.capture());

        SendMessage actual = argumentCaptor.getValue();

        Assertions.assertThat(actual.getParameters().get("chat_id"))
                .isEqualTo(update.message().chat().id());
        Assertions.assertThat(actual.getParameters().get("text"))
                .isEqualTo("Если удобно пообщаться в телеграмме \n" +
                        "жми кнопку **ПОЗВАТЬ ВОЛОНТЕРА** и он с тобой свяжется.\n" +
                        "Если хочешь пообщаться по телефону \n" +
                        "жми кнопку **СТАТЬ ДРУГОМ** и следуй инструкции");
    }

//       как проверить исключение?
//    @Test
//    public void checkException() throws URISyntaxException, IOException {
//        String json = Files.readString(Path.of(TelegramBotUpdatesListenerTests.class.getResource("update.json").toURI()));
//        Update update = BotUtils.fromJson(json.replace("%text%", "/start"), Update.class);
//
//        assertThrows(Exception.class, () -> telegramBotUpdatesListener.process(Collections.singletonList(update)));
//    }



    //     как проверить блок switch   ???
}
