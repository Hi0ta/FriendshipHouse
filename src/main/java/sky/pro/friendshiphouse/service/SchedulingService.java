package sky.pro.friendshiphouse.service;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.request.SendMessage;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import sky.pro.friendshiphouse.model.Adopter;
import sky.pro.friendshiphouse.model.Volunteer;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

@Component
public class SchedulingService {
// Если усыновитель не прислал отчет, напомнить об этом
// Если проходит более 2 дней, то отправлять запрос волонтеру на связь с усыновителем
// Как только период в 30 дней заканчивается, волонтеры принимают решение


    private final TelegramBot telegramBot;
    private final AdopterService adopterService;
    private final VolunteerService volunteerService;


    public SchedulingService(TelegramBot telegramBot, AdopterService adopterService, VolunteerService volunteerService) {
        this.telegramBot = telegramBot;
        this.adopterService = adopterService;
        this.volunteerService = volunteerService;
    }

    @Scheduled(cron = "0 0 21 * * *")
    public void run() {
        Collection<Volunteer> volunteers = volunteerService.getAllVolunteer();
        for (Volunteer volunteer : volunteers) {
            SendMessage checkingReportMessage = new SendMessage(volunteer.getVolunteerChatId(), "Пора проверять отчеты");
            telegramBot.execute(checkingReportMessage); //можно в сообщения добавить список Id отчетов ожидающих проверки
        }
        List<Adopter> adopters = adopterService.getAdoptersAvailabilityReport(LocalDate.now());
        for (Adopter adopter : adopters) {
            SendMessage notReportMessage = new SendMessage(adopter.getAdopterChatId(), "Не могу найти отчет от тебя за сегодня.");
            telegramBot.execute(notReportMessage);
        }
        List<Adopter> adoptersList = adopterService.getAdoptersAvailabilityReport(LocalDate.now().minusDays(1));
        SendMessage adoptersListMessage = new SendMessage(volunteerService.getVolunteerById(1).getVolunteerChatId(),
                "Список усыновителей которые не отправляют отчет:" + adoptersList);
        telegramBot.execute(adoptersListMessage);

        List<Adopter> trialPeriodFinal = adopterService.getAdoptersTrialPeriodFinal();
        if (trialPeriodFinal.size() != 0) {
            SendMessage trialPeriodFinalMessage = new SendMessage(volunteerService.getVolunteerById(1).getVolunteerChatId(),
                    "Список усыновителей которые 30 дней присылали отчет:" + trialPeriodFinal);
            telegramBot.execute(trialPeriodFinalMessage);
        }
    }
}
