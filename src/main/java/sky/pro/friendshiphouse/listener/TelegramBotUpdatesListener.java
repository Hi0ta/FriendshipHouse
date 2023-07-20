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
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;


//@Component так было первоначально
@Service
public class TelegramBotUpdatesListener implements UpdatesListener {

    private final Logger logger = LoggerFactory.getLogger(TelegramBotUpdatesListener.class);

    private final TelegramBot telegramBot;

    public TelegramBotUpdatesListener(TelegramBot telegramBot) {
        this.telegramBot = telegramBot;
        telegramBot.execute(new SetMyCommands(
                new BotCommand("/dog", "Собаки"),
                new BotCommand("/cat", "Кошки"),
                new BotCommand("/volunteer", "Позвать волонтера")));
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
                        case "dogButton":
                            SendMessage dogMessage = new SendMessage(chatId, "Выбери интересующий тебя раздел в доме дружбы для собак:");
                            InlineKeyboardButton dogInfo = new InlineKeyboardButton("Общая информация");
                            dogInfo.callbackData("dogInfo");
                            InlineKeyboardButton dogConsult = new InlineKeyboardButton("Советы и консультации");
                            dogConsult.callbackData("dogConsult");
                            InlineKeyboardButton dogReport = new InlineKeyboardButton("Оправить отчет");
                            dogReport.callbackData("dogReport");
                            Keyboard keyboardDog = new InlineKeyboardMarkup(dogInfo).addRow(dogConsult).addRow(dogReport);
                            dogMessage.replyMarkup(keyboardDog);
                            telegramBot.execute(dogMessage);
                            break;
                        case "dogInfo":
                            SendMessage dogInfoMessage = new SendMessage(chatId, "Здесь должна быть информация о приюте(его назначение и суть)");
                            InlineKeyboardButton dogAddress = new InlineKeyboardButton("Часы работы, адресс, схема проезда");
                            dogAddress.callbackData("dogAddress");
                            InlineKeyboardButton dogRecommendations = new InlineKeyboardButton("Техника безопасности");
                            dogRecommendations.callbackData("dogRecommendations");
                            Keyboard keyboardDogRecommendations = new InlineKeyboardMarkup(dogAddress).addRow(dogRecommendations);
                            dogInfoMessage.replyMarkup(keyboardDogRecommendations);
                            telegramBot.execute(dogInfoMessage);
                            break;
                        case "dogConsult":
                            SendMessage dogConsultMessage = new SendMessage(chatId, "Здесь должна быть информация о предоставляемых консультациях");
                            InlineKeyboardButton dogIntroduction = new InlineKeyboardButton("Правила знакомства");
                            dogIntroduction.callbackData("dogIntroduction");
                            InlineKeyboardButton dogDocList = new InlineKeyboardButton("Список документов необходимых для усыновления");
                            dogDocList.callbackData("dogDocList");
                            InlineKeyboardButton dogTransportation = new InlineKeyboardButton("Транспортировка");
                            dogTransportation.callbackData("dogTransportation");
                            InlineKeyboardButton dogHomeImprovement = new InlineKeyboardButton("Обустройство дома");
                            dogHomeImprovement.callbackData("dogHomeImprovement");
                            InlineKeyboardButton advice = new InlineKeyboardButton("Советы кинолога");
                            advice.callbackData("advice");
                            InlineKeyboardButton cynologistList = new InlineKeyboardButton("Список кинологов");
                            cynologistList.callbackData("cynologistList");
                            InlineKeyboardButton dogReasonRefusal = new InlineKeyboardButton("Причины отказа");
                            dogReasonRefusal.callbackData("dogReasonRefusal");
                            Keyboard keyboardDogConsult = new InlineKeyboardMarkup(dogIntroduction).addRow(dogDocList)
                                    .addRow(dogTransportation).addRow(dogHomeImprovement).addRow(advice)
                                    .addRow(cynologistList).addRow(dogReasonRefusal);
                            dogConsultMessage.replyMarkup(keyboardDogConsult);
                            telegramBot.execute(dogConsultMessage);
                            break;
                        case "dogReport":
                            SendMessage dogReportMessage = new SendMessage(chatId,"Здесь должна быть информация об отчете(что он должен содержать) и предупреждение о том что он должен быть ЕЖЕДНЕВНЫМ");
                            InlineKeyboardButton sendDogReport = new InlineKeyboardButton("Отправить отчет");
                            sendDogReport.callbackData("sendDogReport");
                            Keyboard keyboardDogReport = new InlineKeyboardMarkup(sendDogReport);
                            dogReportMessage.replyMarkup(keyboardDogReport);
                            telegramBot.execute(dogReportMessage);
                            break;
                        case "dogAddress":
                            SendMessage dogAddressMessage = new SendMessage(chatId, """
                                    тел: +7 ХХХ ХХХ ХХ ХХ\s
                                    email: friendshipHouseDog@nemail.com\s
                                    часы работы: ежедневно с 9-00 до 18-00\s
                                    г. ГородN, ул. Улица, д. ХХ\s
                                    тел. охраны: +7 ХХХ ХХХ ХХ ХХ\s
                                    (для оформления пропуска на машину)\s
                                    схема проезда:""");
                            telegramBot.execute(dogAddressMessage);
                            try {
                                byte[] map = Files.readAllBytes(Paths.get(TelegramBotUpdatesListener.class.getResource("/MapDog.jpg").toURI()));
                                SendPhoto sendPhoto = new SendPhoto(chatId, map);
                                telegramBot.execute(sendPhoto);
                            } catch (IOException | URISyntaxException e) {
                                throw new RuntimeException(e);
                            }
                            break;
                        case "dogRecommendations":
                            SendMessage dogRecommendationsMessage = new SendMessage(chatId, "здесь должны быть рекомендации о технике безопасности  на территории приюта");
                            telegramBot.execute(dogRecommendationsMessage);
                            break;
                        case "dogIntroduction":
                            SendMessage dogIntroductionMessage = new SendMessage(chatId, "здесь должны быть правила знакомства");
                            telegramBot.execute(dogIntroductionMessage);
                            break;
                        case "dogDocList":
                            SendMessage dogDocListMessage = new SendMessage(chatId, "здесь должен быть список документов необходимых для усыновления");
                            telegramBot.execute(dogDocListMessage);
                            break;
                        case "dogTransportation":
                            SendMessage dogTransportationMessage = new SendMessage(chatId, "здесь должна быть инфа о транспортировке животного");
                            telegramBot.execute(dogTransportationMessage);
                            break;
                        case "dogHomeImprovement":
                            SendMessage dogHomeImprovementMessage = new SendMessage(chatId, "выберите категорию");
                            InlineKeyboardButton homePuppy = new InlineKeyboardButton("домик для щенка");
                            homePuppy.callbackData("homePuppy");
                            InlineKeyboardButton homeDog = new InlineKeyboardButton("домик для собаки");
                            homeDog.callbackData("homeDog");
                            InlineKeyboardButton homeDogWithoutVision = new InlineKeyboardButton("для собаки с ограниченным зрением");
                            homeDogWithoutVision.callbackData("homeDogWithoutVision");
                            InlineKeyboardButton homeDogWithoutMovement = new InlineKeyboardButton("для собаки с ограниченным передвижением");
                            homeDogWithoutMovement.callbackData("homeDogWithoutMovement");
                            Keyboard keyboardDogHomeImprovementMessage = new InlineKeyboardMarkup(homePuppy).addRow(homeDog).addRow(homeDogWithoutVision).addRow(homeDogWithoutMovement);
                            dogHomeImprovementMessage.replyMarkup(keyboardDogHomeImprovementMessage);
                            telegramBot.execute(dogHomeImprovementMessage);
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
                        case "dogReasonRefusal":
                            SendMessage dogReasonRefusalMessage = new SendMessage(chatId, "здесь должны быть перечислены возможные причины отказа в усыновлении");
                            telegramBot.execute(dogReasonRefusalMessage);
                            break;
                        case "sendDogReport":
// здесь должен быть метод, который принимает отчет и заносит его в БД, а так же распределяет на проверку волонтеру.
                            break;
                        case "catButton":
                            SendMessage catMessage = new SendMessage(chatId, "Выбери интересующий тебя раздел в доме дружбы для кошек:");
                            InlineKeyboardButton catInfo = new InlineKeyboardButton("Общая информация");
                            catInfo.callbackData("catInfo");
                            InlineKeyboardButton catConsult = new InlineKeyboardButton("Советы и консультации");
                            catConsult.callbackData("catConsult");
                            InlineKeyboardButton catReport = new InlineKeyboardButton("Оправить отчет");
                            catReport.callbackData("catReport");
                            Keyboard keyboardCat = new InlineKeyboardMarkup(catInfo).addRow(catConsult).addRow(catReport);
                            catMessage.replyMarkup(keyboardCat);
                            telegramBot.execute(catMessage);
                            break;
                        case "catInfo":
                            SendMessage catInfoMessage = new SendMessage(chatId, "Здесь должна быть информация о приюте(его назначение и суть)");
                            InlineKeyboardButton catAddress = new InlineKeyboardButton("Часы работы, адресс, схема проезда");
                            catAddress.callbackData("catAddress");
                            InlineKeyboardButton catRecommendations = new InlineKeyboardButton("Техника безопасности");
                            catRecommendations.callbackData("catRecommendations");
                            Keyboard keyboardCatRecommendations = new InlineKeyboardMarkup(catAddress).addRow(catRecommendations);
                            catInfoMessage.replyMarkup(keyboardCatRecommendations);
                            telegramBot.execute(catInfoMessage);
                            break;
                        case "catConsult":
                            SendMessage catConsultMessage = new SendMessage(chatId, "Здесь должна быть информация о предоставляемых консультациях");
                            InlineKeyboardButton catIntroduction = new InlineKeyboardButton("Правила знакомства");
                            catIntroduction.callbackData("catIntroduction");
                            InlineKeyboardButton catDocList = new InlineKeyboardButton("Список документов необходимых для усыновления");
                            catDocList.callbackData("catDocList");
                            InlineKeyboardButton catTransportation = new InlineKeyboardButton("Транспортировка");
                            catTransportation.callbackData("catTransportation");
                            InlineKeyboardButton catHomeImprovement = new InlineKeyboardButton("Обустройство дома");
                            catHomeImprovement.callbackData("catHomeImprovement");
                            InlineKeyboardButton catReasonRefusal = new InlineKeyboardButton("Причины отказа");
                            catReasonRefusal.callbackData("catReasonRefusal");
                            Keyboard keyboardCatConsult = new InlineKeyboardMarkup(catIntroduction).addRow(catDocList)
                                    .addRow(catTransportation).addRow(catHomeImprovement).addRow(catReasonRefusal);
                            catConsultMessage.replyMarkup(keyboardCatConsult);
                            telegramBot.execute(catConsultMessage);
                            break;
                        case "catReport":
                            SendMessage catReportMessage = new SendMessage(chatId,"Здесь должна быть информация об отчете(что он должен содержать) и предупреждение о том что он должен быть ЕЖЕДНЕВНЫМ");
                            InlineKeyboardButton sendCatReport = new InlineKeyboardButton("Отправить отчет");
                            sendCatReport.callbackData("sendCatReport");
                            Keyboard keyboardCatReport = new InlineKeyboardMarkup(sendCatReport);
                            catReportMessage.replyMarkup(keyboardCatReport);
                            telegramBot.execute(catReportMessage);
                            break;
                        case "catAddress":
                            SendMessage catAddressMessage = new SendMessage(chatId, """
                                    тел: +7 ХХХ ХХХ ХХ ХХ\s
                                    email: friendshipHouseCat@nemail.com\s
                                    часы работы: ежедневно с 9-00 до 18-00\s
                                    г. ГородN, ул. Улица, д. ХХ\s
                                    тел. охраны: +7 ХХХ ХХХ ХХ ХХ\s
                                    (для оформления пропуска на машину)\s
                                    схема проезда:""");
                            telegramBot.execute(catAddressMessage);
                            try {
                                byte[] map = Files.readAllBytes(Paths.get(TelegramBotUpdatesListener.class.getResource("/MapCat.jpg").toURI()));
                                SendPhoto sendPhoto2 = new SendPhoto(chatId, map);
                                telegramBot.execute(sendPhoto2);
                            } catch (IOException | URISyntaxException e) {
                                throw new RuntimeException(e);
                            }
                            break;
                        case "catRecommendations":
                            SendMessage catRecommendationsMessage = new SendMessage(chatId, "здесь должны быть рекомендации о технике безопасности  на территории приюта");
                            telegramBot.execute(catRecommendationsMessage);
                            break;
                        case "catIntroduction":
                            SendMessage catIntroductionMessage = new SendMessage(chatId, "здесь должны быть правила знакомства");
                            telegramBot.execute(catIntroductionMessage);
                            break;
                        case "catDocList":
                            SendMessage catDocListMessage = new SendMessage(chatId, "здесь должен быть список документов необходимых для усыновления");
                            telegramBot.execute(catDocListMessage);
                            break;
                        case "catTransportation":
                            SendMessage catTransportationMessage = new SendMessage(chatId, "здесь должна быть инфа о транспортировке животного");
                            telegramBot.execute(catTransportationMessage);
                            break;
                        case "catHomeImprovement":
                            SendMessage catHomeImprovementMessage = new SendMessage(chatId, "выберите категорию");
                            InlineKeyboardButton homeKitty = new InlineKeyboardButton("домик для котенка");
                            homeKitty.callbackData("homeKitty");
                            InlineKeyboardButton homeCat = new InlineKeyboardButton("домик для кошки");
                            homeCat.callbackData("homeCat");
                            InlineKeyboardButton homeCatWithoutVision = new InlineKeyboardButton("для кошки с ограниченным зрением");
                            homeCatWithoutVision.callbackData("homeCatWithoutVision");
                            InlineKeyboardButton homeCatWithoutMovement = new InlineKeyboardButton("для кошки с ограниченным передвижением");
                            homeCatWithoutMovement.callbackData("homeCatWithoutMovement");
                            Keyboard keyboardCatHomeImprovementMessage = new InlineKeyboardMarkup(homeKitty).addRow(homeCat).addRow(homeCatWithoutVision).addRow(homeCatWithoutMovement);
                            catHomeImprovementMessage.replyMarkup(keyboardCatHomeImprovementMessage);
                            telegramBot.execute(catHomeImprovementMessage);
                            break;
                        case "homeKitty":
                            SendMessage homeKittyMessage = new SendMessage(chatId, "здесь должна быть инфа об обустройстве дома для котят");
                            telegramBot.execute(homeKittyMessage);
                            break;
                        case "homeCat":
                            SendMessage homeCatMessage = new SendMessage(chatId, "здесь должна быть инфа об обустройстве дома для взрослых кошек");
                            telegramBot.execute(homeCatMessage);
                            break;
                        case "homeCatWithoutVision":
                            SendMessage homeCatWithoutVisionMessage = new SendMessage(chatId, "здесь должна быть инфа об обустройстве дома для кошек с ограниченным зрением");
                            telegramBot.execute(homeCatWithoutVisionMessage);
                            break;
                        case "homeCatWithoutMovement":
                            SendMessage homeCatWithoutMovementMessage = new SendMessage(chatId, "здесь должна быть инфа об обустройстве дома для кощек с ограниченным передвижением");
                            telegramBot.execute(homeCatWithoutMovementMessage);
                            break;
                        case "catReasonRefusal":
                            SendMessage catReasonRefusalMessage = new SendMessage(chatId, "здесь должны быть перечислены возможные причины отказа в усыновлении");
                            telegramBot.execute(catReasonRefusalMessage);
                            break;
                        case "sendCatReport":
// здесь должен быть метод, который принимает отчет и заносит его в БД, а так же распределяет на проверку волонтеру.
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
                            "тебя приветствует чат бот дома дружбы для животных. \uD83D\uDE00 \n" + // Home.getName() + (название дома для животных)
                            "У нас есть дом для собак и отдельный дом для кошек. \n" +
                            "Я могу о них тебе рассказать \n" +
                            "Если чего-то не найдешь всегда сможешь позвать волонтера. \n" +
                            "(это cамый нижний пункт в меню) \n" +
                            "Итак,  выбери с кем желаешь дружить?");
                    InlineKeyboardButton dogButton = new InlineKeyboardButton("Собаки");
                    dogButton.callbackData("dogButton");
                    InlineKeyboardButton catButton = new InlineKeyboardButton("Кошки");
                    catButton.callbackData("catButton");
                    Keyboard keyboardStart = new InlineKeyboardMarkup(dogButton, catButton);
                    welcomeMessage.replyMarkup(keyboardStart);
                    telegramBot.execute(welcomeMessage);
                } else if ("/dog".equals(textIncomingMessage)) {
                    SendMessage dogMessage = new SendMessage(chatId, "Выбери интересующий тебя раздел:");
                    InlineKeyboardButton dogInfo = new InlineKeyboardButton("Общая информация");
                    dogInfo.callbackData("dogInfo");
                    InlineKeyboardButton dogConsult = new InlineKeyboardButton("Советы и консультации");
                    dogConsult.callbackData("dogConsult");
                    InlineKeyboardButton dogReport = new InlineKeyboardButton("Оправить отчет");
                    dogReport.callbackData("dogReport");
                    Keyboard keyboardDog = new InlineKeyboardMarkup(dogInfo).addRow(dogConsult).addRow(dogReport);
                    dogMessage.replyMarkup(keyboardDog);
                    telegramBot.execute(dogMessage);
                } else if ("/cat".equals(textIncomingMessage)) {
                    SendMessage catMessage = new SendMessage(chatId, "Выбери интересующий тебя раздел:");
                    InlineKeyboardButton catInfo = new InlineKeyboardButton("Общая информация");
                    catInfo.callbackData("catInfo");
                    InlineKeyboardButton catConsult = new InlineKeyboardButton("Советы и консультации");
                    catConsult.callbackData("catConsult");
                    InlineKeyboardButton catReport = new InlineKeyboardButton("Оправить отчет");
                    catReport.callbackData("catReport");
                    Keyboard keyboardCat = new InlineKeyboardMarkup(catInfo).addRow(catConsult).addRow(catReport);
                    catMessage.replyMarkup(keyboardCat);
                    telegramBot.execute(catMessage);
                } else if ("/volunteer".equals(textIncomingMessage)) {
                    SendMessage volunteerMessage = new SendMessage(chatId, "Если удобно пообщаться в телеграмме \n" +
                            "жми кнопку **ПОЗВАТЬ ВОЛОНТЕРА** и он с тобой свяжется.\n" +
                            "Если хочешь пообщаться по телефону \n" +
                            "жми кнопку **СТАТЬ ДРУГОМ** и следуй инструкции");
                    InlineKeyboardButton sendVolunteer = new InlineKeyboardButton("ПОЗВАТЬ ВОЛОНТЕРА");
                    sendVolunteer.callbackData("sendVolunteer");
                    InlineKeyboardButton sendCallMeBack = new InlineKeyboardButton("ЗВОНОК ДРУГУ");
                    sendCallMeBack.callbackData("sendCallMeBack");
                    Keyboard keyboardVolunteer = new InlineKeyboardMarkup(sendVolunteer, sendCallMeBack);
                    volunteerMessage.replyMarkup(keyboardVolunteer);
                    telegramBot.execute(volunteerMessage);
                }
            });
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return UpdatesListener.CONFIRMED_UPDATES_ALL;
    }
}