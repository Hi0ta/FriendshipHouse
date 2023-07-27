package sky.pro.friendshiphouse.listener;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.*;
import com.pengrad.telegrambot.model.request.InlineKeyboardButton;
import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;
import com.pengrad.telegrambot.model.request.Keyboard;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.request.SetMyCommands;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import sky.pro.friendshiphouse.model.Adopter;
import sky.pro.friendshiphouse.service.AdopterService;
import sky.pro.friendshiphouse.service.ButtonService;
import sky.pro.friendshiphouse.service.FriendService;
import sky.pro.friendshiphouse.service.ReportService;

import javax.annotation.PostConstruct;
import java.util.List;

@Service
public class TelegramBotUpdatesListener implements UpdatesListener {

    private final Logger logger = LoggerFactory.getLogger(TelegramBotUpdatesListener.class);

    private final TelegramBot telegramBot;
    private final FriendService friendService;
    private final AdopterService adopterService;
    private final ReportService reportService;
    private final ButtonService buttonService;

    public TelegramBotUpdatesListener(TelegramBot telegramBot, FriendService friendService, AdopterService adopterService, ReportService reportService, ButtonService buttonService) {
        this.friendService = friendService;
        this.telegramBot = telegramBot;
        this.adopterService = adopterService;
        this.reportService = reportService;
        this.buttonService = buttonService;
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
            updates.stream()
                    .filter(update -> update.message() != null)
                    .forEach(update -> {
                        logger.info("Handles update: {}", update);
                        if (update.callbackQuery() != null) {
                            Long chatId = update.callbackQuery().message().chat().id();
                            CallbackQuery callbackQuery = update.callbackQuery();
                            String data = callbackQuery.data();
                            buttonService.buttonSelection(data, chatId);
                            return;
                        } else if (update.message().photo() != null) {
                            String photoSize = update.message().photo()[1].fileId();
                            if (reportService.editReportPhoto(update.message().chat().id(), photoSize)) { //если true
                                SendMessage reportPhotoMessage = new SendMessage(update.message().chat().id(), "Фото принято и отправлено на проверку волонтеру.");
                                telegramBot.execute(reportPhotoMessage);
                            } else {
                                SendMessage reportNotFound = new SendMessage(update.message().chat().id(), "Сначала отправь текст отчета.");
                                telegramBot.execute(reportNotFound);
                            }
                            return;
                        } else if (update.message().text() == null) {
                            SendMessage FalseMessage = new SendMessage(update.message().chat().id(), "Введенное сообщение не соответствует заданному формату, попробуй еще разок");
                            telegramBot.execute(FalseMessage);
                            return;
                        }
                        Message message = update.message();
                        String name = update.message().from().firstName();
                        Long chatId = message.chat().id();
                        String textIncomingMessage = message.text();
                        if ("/start".equals(textIncomingMessage)) {
                            SendMessage welcomeMessage = new SendMessage(chatId, "Дорогой друг, " + name + ", \n" +
                                    "тебя приветствует чат бот дома дружбы для животных. \uD83D\uDE00 \n" +
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
                            friendService.createFriend(chatId, name);
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
                            SendMessage volunteerMessage = new SendMessage(chatId, """
                                    Если удобно пообщаться в телеграмме\s
                                    жми кнопку *ПОЗВАТЬ ВОЛОНТЕРА* и он с тобой свяжется.\s
                                    Если хочешь пообщаться по телефону \s
                                    жми кнопку *ПОЗВОНИТЕ МНЕ* и следуй инструкции""");
                            InlineKeyboardButton callVolunteer = new InlineKeyboardButton("ПОЗВАТЬ ВОЛОНТЕРА");
                            callVolunteer.callbackData("callVolunteer");
                            InlineKeyboardButton CallMeBack = new InlineKeyboardButton("ПОЗВОНИТЕ МНЕ");
                            CallMeBack.callbackData("CallMeBack");
                            Keyboard keyboardVolunteer = new InlineKeyboardMarkup(callVolunteer, CallMeBack);
                            volunteerMessage.replyMarkup(keyboardVolunteer);
                            telegramBot.execute(volunteerMessage);
//далее распознавание сообщений от друзей/усыновителей
                        } else if (textIncomingMessage.matches("(8)([0-9]{10})")) {
                            friendService.changeFriend(chatId, textIncomingMessage);
                            SendMessage telTrueMessage = new SendMessage(chatId, "Если свой номер написал верно ожидай звонка в ближайшее время");
                            telegramBot.execute(telTrueMessage);
                            friendService.callMe(chatId);
                        } else if (textIncomingMessage.matches("(отчет|ОТЧЕТ)([А-Яа-яA-Za-z0-9\\p{P} )]+)")) {
                            String reportMessage = update.message().text().substring(6);
                            Adopter adopter = adopterService.getAdopterByChatId(chatId);
                            reportService.createReport(reportMessage, adopter);
                            SendMessage reportTextMessage = new SendMessage(chatId, """
                                    Текст отчета принят и отправлен на проверку волонтеру.\s
                                    Еще необходимо прислать фото!!!""");
                            telegramBot.execute(reportTextMessage);
//далее идут команды которые волонтер знает как писать и пишет вручную:
                        } else if (textIncomingMessage.matches("(0adopterID:)([0-9]+)")) {
                            String adopterId = update.message().text().substring(11);
                            Long adopterChatId = adopterService.getAdopterByChatId(Long.parseLong(adopterId, 10)).getAdopterChatId();
                            SendMessage congratulateMessage = new SendMessage(adopterChatId, "Поздравляем - испытательный срок пройден!!!");
                            telegramBot.execute(congratulateMessage);
                        } else if (textIncomingMessage.matches("(14adopterID:)([0-9]+)")) {
                            String adopterId = update.message().text().substring(12);
                            Long adopterChatId = adopterService.getAdopterByChatId(Long.parseLong(adopterId, 10)).getAdopterChatId();
                            SendMessage moreDaysMessage14 = new SendMessage(adopterChatId, "Испытательный срок продлен на 14 дней");
                            telegramBot.execute(moreDaysMessage14);
                        } else if (textIncomingMessage.matches("(30adopterID:)([0-9]+)")) {
                            String adopterId = update.message().text().substring(12);
                            Long adopterChatId = adopterService.getAdopterByChatId(Long.parseLong(adopterId, 10)).getAdopterChatId();
                            SendMessage moreDaysMessage30 = new SendMessage(adopterChatId, "Испытательный срок продлен на 30 дней");
                            telegramBot.execute(moreDaysMessage30);
                        } else if (textIncomingMessage.matches("(notPassedID:)([0-9]+)")) {
                            String adopterIdString = update.message().text().substring(13);
                            long adopterId = Long.parseLong(adopterIdString, 10);
                            Long adopterChatId = adopterService.getAdopterByChatId(adopterId).getAdopterChatId();
                            SendMessage notPassedMessage = new SendMessage(adopterChatId, "Испытательный срок не пройден. ЗДЕСЬ должна быть инструкция по дальнейшим шагам");
                            adopterService.editAdopterStatusBlackList(adopterId); // меняет статус усыновителя на - заблокирован.
                            telegramBot.execute(notPassedMessage);
                        } else if (textIncomingMessage.matches("(remakeID:)([0-9]+)")) {
                            String adopterId = update.message().text().substring(10);
                            Long adopterChatId = adopterService.getAdopterByChatId(Long.parseLong(adopterId, 10)).getAdopterChatId();
                            SendMessage remakeMessage = new SendMessage(adopterChatId, "Дорогой усыновитель, мы заметили, что ты заполняешь отчет не так подробно, как необходимо. Пожалуйста, подойди ответственнее к этому занятию. В противном случае волонтеры приюта будут обязаны самолично проверять условия содержания животного");
                            telegramBot.execute(remakeMessage);
                        } else {
                            SendMessage FalseMessage = new SendMessage(chatId, "Введенное сообщение не соответствует заданному формату, попробуй еще разок");
                            telegramBot.execute(FalseMessage);
                        }
                    });
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return UpdatesListener.CONFIRMED_UPDATES_ALL;
    }
}