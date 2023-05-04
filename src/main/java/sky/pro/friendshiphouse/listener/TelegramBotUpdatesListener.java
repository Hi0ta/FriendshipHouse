package sky.pro.friendshiphouse.listener;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.BotCommand;
import com.pengrad.telegrambot.model.CallbackQuery;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.InlineKeyboardButton;
import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;
import com.pengrad.telegrambot.model.request.Keyboard;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.request.SendPhoto;
import com.pengrad.telegrambot.request.SetMyCommands;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;


@Component
public class TelegramBotUpdatesListener implements UpdatesListener {

    private final Logger logger = LoggerFactory.getLogger(TelegramBotUpdatesListener.class);

    private final TelegramBot telegramBot;

    public TelegramBotUpdatesListener(TelegramBot telegramBot) {
        this.telegramBot = telegramBot;
        telegramBot.execute(new SetMyCommands(
                new BotCommand("/info", "1) Информация о приюте"),
                new BotCommand("/consult", "2) Советы и консультации"),
                new BotCommand("/report", "3) Отправить отчет"),
                new BotCommand("/volunteer", "4) Позвать волонтера")));
    }

    @PostConstruct
    public void init() {
        telegramBot.setUpdatesListener(this);
    }


    @Override
    public int process(List<Update> updates) {
        try {
            updates.forEach(update -> {
                logger.info("Handles update: {}", update);

                if (update.callbackQuery() != null) {
                    Long chatId = update.callbackQuery().message().chat().id();
                    CallbackQuery callbackQuery = update.callbackQuery();
                    String data = callbackQuery.data();
                    switch (data) {
                        case "address":
                            SendMessage addressMessage = new SendMessage(chatId, "тел: +7 ХХХ ХХХ ХХ ХХ \n" +
                                    "email: friendshipHouse@nemail.com \n" +
                                    "часы работы: ежедневно с 9-00 до 18-00 \n" +
                                    "г. ГородN, ул. Улица, д. ХХ \n" +
                                    "схема проезда:");
                            telegramBot.execute(addressMessage);
                            try {
                                byte[] map = Files.readAllBytes(Paths.get(TelegramBotUpdatesListener.class.getResource("/Map.jpg").toURI()));
                                SendPhoto sendPhoto = new SendPhoto(chatId, map);
                                telegramBot.execute(sendPhoto);
                            } catch (IOException | URISyntaxException e) {
                                throw new RuntimeException(e);
                            }
                            break;
                        case "recommendations":
                            SendMessage recommendationsMessage = new SendMessage(chatId, "здесь должны быть рекомендации и техника безопасности");
                            telegramBot.execute(recommendationsMessage);
                            break;
                        case "introduction":
                            SendMessage introductionMessage = new SendMessage(chatId, "здесь должны быть правила знакомства");
                            telegramBot.execute(introductionMessage);
                            break;
                        case "docList":
                            SendMessage docListMessage = new SendMessage(chatId, "здесь должен быть список документов необходимых для усыновления");
                            telegramBot.execute(docListMessage);
                            break;
                        case "transportation":
                            SendMessage transportationMessage = new SendMessage(chatId, "здесь должна быть инфа о транспортировке животного");
                            telegramBot.execute(transportationMessage);
                            break;
                        case "homeImprovement":
                            SendMessage homeImprovementMessage = new SendMessage(chatId, "выберите категорию");
                            InlineKeyboardButton homePuppy = new InlineKeyboardButton("домик для щенка");
                            homePuppy.callbackData("homePuppy");
                            InlineKeyboardButton homeDog = new InlineKeyboardButton("домик для собаки");
                            homeDog.callbackData("homeDog");
                            InlineKeyboardButton homeDogWithoutVision = new InlineKeyboardButton("для собаки с ограниченным зрением");
                            homeDogWithoutVision.callbackData("homeDogWithoutVision");
                            InlineKeyboardButton homeDogWithoutMovement = new InlineKeyboardButton("для собаки с ограниченным передвижением");
                            homeDogWithoutMovement.callbackData("homeDogWithoutMovement");

                            Keyboard keyboard = new InlineKeyboardMarkup(homePuppy).addRow(homeDog).addRow(homeDogWithoutVision).addRow(homeDogWithoutMovement);
                            homeImprovementMessage.replyMarkup(keyboard);
                            telegramBot.execute(homeImprovementMessage);
                            break;
                        case "homePuppy":
                            SendMessage homePuppyMessage = new SendMessage(chatId, "здесь должна быть инфа об обустройстве дома для щенков");
                            telegramBot.execute(homePuppyMessage);
                            break;
                        case "homeDog":
                            SendMessage homeDogMessage = new SendMessage(chatId, "здесь должна быть инфа об обустройстве дома для взрослых собак");
                            telegramBot.execute(homeDogMessage);
                            break;
                        case "homeDogWithoutVision":
                            SendMessage homeDogWithoutVisionMessage = new SendMessage(chatId, "здесь должна быть инфа об обустройстве дома для собак с ограниченным зрением");
                            telegramBot.execute(homeDogWithoutVisionMessage);
                            break;
                        case "homeDogWithoutMovement":
                            SendMessage homeDogWithoutMovementMessage = new SendMessage(chatId, "здесь должна быть инфа об обустройстве дома для собак с ограниченным передвижением");
                            telegramBot.execute(homeDogWithoutMovementMessage);
                            break;
                        case "advice":
                            SendMessage adviceMessage = new SendMessage(chatId, "здесь должны быть советы кинолога");
                            telegramBot.execute(adviceMessage);
                            break;
                        case "cynologistList":
                            SendMessage cynologistListMessage = new SendMessage(chatId, "здесь должен быть список кинологов");
                            telegramBot.execute(cynologistListMessage);
                            break;
                        case "refusal":
                            SendMessage refusalMessage = new SendMessage(chatId, "здесь должны быть перечислены возможные причины отказа в усыновлении");
                            telegramBot.execute(refusalMessage);
                            break;
                        case "sendReport":
// здесь должен быть метод который принимает отчет и заносит его в БД, а так же распределяет на проверку волонтеру.
                            break;
                        case "sendVolunteer":
// здесь должен быть метод позвать волонтера (свободному волонтеру в телегу приходит сообщение о том что посетитель желает пообщаться и ссылка на телегу посетителя)
                            break;
                        case "sendCallMeBack":
                            SendMessage friendNameMessage = new SendMessage(chatId, "Для звонка нам понадобятся твои контактные данные. \n" +
                                    "Напиши имя (так наш волонтер сможет к тебе обращаться)");
                            telegramBot.execute(friendNameMessage);
// здесь нужно принять Имя от посетителя и записать в БД
                            SendMessage friendTelMessage = new SendMessage(chatId, "Напиши свой номер телефона для связи \n" +
                                    "в формате +7-ХХХ-ХХХ-ХХ-ХХ");
                            telegramBot.execute(friendTelMessage);
// здесь нужно принять номер телефона от посетителя записать в БД, и передать ВСЕ для связи свободному волонтеру.
                            SendMessage friendMessage = new SendMessage(chatId,
                                    "Если все данные верны ожидай звонка в ближайшее время");
                            telegramBot.execute(friendMessage);
                            break;
                    }
                    return;
                }
//  if (updates.stream().filter((Update update) -> update.message() != null)) - здесь нужно проверить что message != null - эта строка не подходит
                Message message = update.message();
                String name = update.message().from().firstName();
                Long chatId = message.chat().id();
                String textIncomingMessage = message.text();

                if ("/start".equals(textIncomingMessage)) {
                    SendMessage welcomeMessage = new SendMessage(chatId, "Дорогой друг, " + name + ", \n" +
                            "тебя приветствует чат бот дома для животных. \uD83D\uDE00 \n" + // Home.getName() + (название дома для животных)
                            "Все что я умею содержится в меню - ознакомься. \n" +
                            "Если чего-то не найдешь всегда сможешь позвать волонтера. \n" +
                            "(это амый нижний пункт в меню)");
                    telegramBot.execute(welcomeMessage);
                } else if ("/info".equals(textIncomingMessage)) {
                    SendMessage infoMessage = new SendMessage(chatId, "Здесь должна быть информация о приюте(его назначение и суть)");

                    InlineKeyboardButton address = new InlineKeyboardButton("Часы работы, адресс, схема проезда");
                    address.callbackData("address");
                    InlineKeyboardButton recommendations = new InlineKeyboardButton("Рекомендации и техника безопасности");
                    recommendations.callbackData("recommendations");

                    Keyboard keyboard1 = new InlineKeyboardMarkup(address).addRow(recommendations);
                    infoMessage.replyMarkup(keyboard1);
                    telegramBot.execute(infoMessage);
                } else if ("/consult".equals(textIncomingMessage)) {
                    SendMessage consultMessage = new SendMessage(chatId, "Здесь должна быть информация о предоставляемых консультациях");

                    InlineKeyboardButton introduction = new InlineKeyboardButton("Правила знакомства");
                    introduction.callbackData("introduction");
                    InlineKeyboardButton docList = new InlineKeyboardButton("Список документов необходимых для усыновления");
                    docList.callbackData("docList");
                    InlineKeyboardButton transportation = new InlineKeyboardButton("Транспортировка");
                    transportation.callbackData("transportation");
                    InlineKeyboardButton homeImprovement = new InlineKeyboardButton("Обустройство дома");
                    homeImprovement.callbackData("homeImprovement");
                    InlineKeyboardButton advice = new InlineKeyboardButton("Советы кинолога");
                    advice.callbackData("advice");
                    InlineKeyboardButton cynologistList = new InlineKeyboardButton("Список кинологов");
                    cynologistList.callbackData("cynologistList");
                    InlineKeyboardButton refusal = new InlineKeyboardButton("Причины отказа");
                    refusal.callbackData("refusal");

                    Keyboard keyboard2 = new InlineKeyboardMarkup(introduction).addRow(docList)
                            .addRow(transportation).addRow(homeImprovement).addRow(advice)
                            .addRow(cynologistList).addRow(refusal);
                    consultMessage.replyMarkup(keyboard2);
                    telegramBot.execute(consultMessage);
                } else if ("/report".equals(textIncomingMessage)) {
                    SendMessage reportMessage = new SendMessage(chatId,
                            "Здесь должна быть информация об отчете(что он должен содержать) и предупреждение о том что он должен быть ЕЖЕДНЕВНЫМ");

                    InlineKeyboardButton sendReport = new InlineKeyboardButton("Отправить отчет");
                    sendReport.callbackData("sendReport");
                    Keyboard keyboard3 = new InlineKeyboardMarkup(sendReport);
                    reportMessage.replyMarkup(keyboard3);
                    telegramBot.execute(reportMessage);
                } else if ("/volunteer".equals(textIncomingMessage)) {
                    SendMessage volunteerMessage = new SendMessage(chatId, "Если удобно пообщаться в телеграмме \n" +
                            "жми кнопку **ПОЗВАТЬ ВОЛОНТЕРА** и он с тобой свяжется.\n" +
                            "Если хочешь пообщаться по телефону \n" +
                            "жми кнопку **СТАТЬ ДРУГОМ** и следуй инструкции");
                    InlineKeyboardButton sendVolunteer = new InlineKeyboardButton("ПОЗВАТЬ ВОЛОНТЕРА");
                    sendVolunteer.callbackData("sendVolunteer");
                    InlineKeyboardButton sendCallMeBack = new InlineKeyboardButton("ЗВОНОК ДРУГУ");
                    sendCallMeBack.callbackData("sendCallMeBack");

                    Keyboard keyboard4 = new InlineKeyboardMarkup(sendVolunteer, sendCallMeBack);
                    volunteerMessage.replyMarkup(keyboard4);
                    telegramBot.execute(volunteerMessage);
                }
            });
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return UpdatesListener.CONFIRMED_UPDATES_ALL;
    }
}