package sky.pro.friendshiphouse.service;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.request.InlineKeyboardButton;
import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;
import com.pengrad.telegrambot.model.request.Keyboard;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.request.SendPhoto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import sky.pro.friendshiphouse.listener.TelegramBotUpdatesListener;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;

@Slf4j
@RequiredArgsConstructor
@Service
public class ButtonService {

    private final TelegramBot telegramBot;
    private final FriendService friendService;

    public void buttonSelection(String data, Long chatId) {
        switch (data) {
            case "dogButton":
                SendMessage dogMessage = new SendMessage(chatId, "Выбери интересующий тебя раздел в доме дружбы для собак:");
                InlineKeyboardButton dogInfo = new InlineKeyboardButton("Общая информация");
                dogInfo.callbackData("dogInfo");
                InlineKeyboardButton dogConsult = new InlineKeyboardButton("Советы и консультации");
                dogConsult.callbackData("dogConsult");
                InlineKeyboardButton dogReport = new InlineKeyboardButton("Отчет о питомце");
                dogReport.callbackData("report");
                Keyboard keyboardDog = new InlineKeyboardMarkup(dogInfo).addRow(dogConsult).addRow(dogReport);
                dogMessage.replyMarkup(keyboardDog);
                telegramBot.execute(dogMessage);
                break;
            case "dogInfo":
                SendMessage dogInfoMessage = new SendMessage(chatId, "Здесь должна быть информация о приюте(его назначение и суть)");
                InlineKeyboardButton dogAddress = new InlineKeyboardButton("Часы работы, адрес, схема проезда");
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
            case "report":
                SendMessage reportMessage = new SendMessage(chatId, """
                                    Отчет должен быть ежедневным !!!\s
                                    Подойди к этой задаче ответственно.\s
                                    В день можно отправить один отчет\s
                                    (1 сообщение с текстом + 1 фото)\s
                                    Текст отчета в одном сообщении, должен начинаться со слова "ОТЧЕТ"\s
                                    Далее по пунктам:\s
                                    1) Рацион животного\s
                                    3) Общее самочувствие и привыкание к новому месту\s
                                    4) Изменение в поведении: отказ от старых привычек, приобретение новых\s
                                    Фото животного - отправляется в последнюю очередь,\s
                                    после сообщения с текстом отчета\s
                                    Волонтеры проверяют отчеты каждый день после 21-00""");
                telegramBot.execute(reportMessage);
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
                SendMessage dogRecommendationsMessage = new SendMessage(chatId, "здесь должны быть рекомендации о технике безопасности  на территории дома для собак");
                telegramBot.execute(dogRecommendationsMessage);
                break;
            case "dogIntroduction":
                SendMessage dogIntroductionMessage = new SendMessage(chatId, "здесь должны быть правила знакомства с собаками");
                telegramBot.execute(dogIntroductionMessage);
                break;
            case "dogDocList":
                SendMessage dogDocListMessage = new SendMessage(chatId, "здесь должен быть список документов необходимых для усыновления собак");
                telegramBot.execute(dogDocListMessage);
                break;
            case "dogTransportation":
                SendMessage dogTransportationMessage = new SendMessage(chatId, "здесь должна быть инфа о транспортировке собак");
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
                SendMessage dogReasonRefusalMessage = new SendMessage(chatId, "здесь должны быть перечислены возможные причины отказа в усыновлении собаки");
                telegramBot.execute(dogReasonRefusalMessage);
                break;
            case "catButton":
                SendMessage catMessage = new SendMessage(chatId, "Выбери интересующий тебя раздел в доме дружбы для кошек:");
                InlineKeyboardButton catInfo = new InlineKeyboardButton("Общая информация");
                catInfo.callbackData("catInfo");
                InlineKeyboardButton catConsult = new InlineKeyboardButton("Советы и консультации");
                catConsult.callbackData("catConsult");
                InlineKeyboardButton catReport = new InlineKeyboardButton("Отчет о питомце");
                catReport.callbackData("report");
                Keyboard keyboardCat = new InlineKeyboardMarkup(catInfo).addRow(catConsult).addRow(catReport);
                catMessage.replyMarkup(keyboardCat);
                telegramBot.execute(catMessage);
                break;
            case "catInfo":
                SendMessage catInfoMessage = new SendMessage(chatId, "Здесь должна быть информация о приюте(его назначение и суть)");
                InlineKeyboardButton catAddress = new InlineKeyboardButton("Часы работы, адрес, схема проезда");
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
                SendMessage catRecommendationsMessage = new SendMessage(chatId, "здесь должны быть рекомендации о технике безопасности  на территории дома для кошек");
                telegramBot.execute(catRecommendationsMessage);
                break;
            case "catIntroduction":
                SendMessage catIntroductionMessage = new SendMessage(chatId, "здесь должны быть правила знакомства с кошками");
                telegramBot.execute(catIntroductionMessage);
                break;
            case "catDocList":
                SendMessage catDocListMessage = new SendMessage(chatId, "здесь должен быть список документов необходимых для усыновления кошек");
                telegramBot.execute(catDocListMessage);
                break;
            case "catTransportation":
                SendMessage catTransportationMessage = new SendMessage(chatId, "здесь должна быть инфа о транспортировке кошек");
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
                SendMessage homeCatWithoutMovementMessage = new SendMessage(chatId, "здесь должна быть инфа об обустройстве дома для кошек с ограниченным передвижением");
                telegramBot.execute(homeCatWithoutMovementMessage);
                break;
            case "catReasonRefusal":
                SendMessage catReasonRefusalMessage = new SendMessage(chatId, "здесь должны быть перечислены возможные причины отказа в усыновлении кошки");
                telegramBot.execute(catReasonRefusalMessage);
                break;
            case "callVolunteer":
                SendMessage callVolunteerMessage = new SendMessage(chatId, "Волонтер скоро с вами свяжется \uD83D\uDE09");
                telegramBot.execute(callVolunteerMessage);
                friendService.callVolunteer(chatId);
                break;
            case "CallMeBack":
                SendMessage sendCallMeBackMessage = new SendMessage(chatId, """
                                    Для звонка нам понадобится твой номер телефона.\s
                                    Напиши его в формате 8ХХХХХХХХХХ""");
                telegramBot.execute(sendCallMeBackMessage);
                break;
        }
    }
}
