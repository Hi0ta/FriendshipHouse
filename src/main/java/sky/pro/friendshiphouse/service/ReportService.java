package sky.pro.friendshiphouse.service;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.request.SendMessage;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import sky.pro.friendshiphouse.constant.ReportStatus;
import sky.pro.friendshiphouse.model.Adopter;
import sky.pro.friendshiphouse.model.Report;
import sky.pro.friendshiphouse.repository.ReportRepository;

import java.time.LocalDate;
import java.util.Collection;

import static sky.pro.friendshiphouse.constant.ReportStatus.AWAITING_VERIFICATION;
import static sky.pro.friendshiphouse.constant.ReportStatus.VERIFIED_ACCEPTED;


@RequiredArgsConstructor
@Service
public class ReportService {
    // Каждый день волонтеры просматривают все присланные отчеты после 21:00. - реализовано через swagger
// как распределяют волонтеры отчеты которые нужно проверить между собой? Может нужен метод распределяющий отчеты к проверке?
    private final ReportRepository reportRepository;
    private final TelegramBot telegramBot;
    private final Logger logger = LoggerFactory.getLogger(ReportService.class);


    /**
     * Позволяет добавить отчет в БД
     */
    public void createReport(String reportMessage, Adopter adopter) {
        logger.info("launching the createReport method");
        long adopterId = adopter.getAdopterId();
        if (reportRepository.getReportByAdopterAdopterIdAndReportDate(adopterId, LocalDate.now()) == null) {
            Report newReport = new Report();
            newReport.setReportStatus(AWAITING_VERIFICATION);
            newReport.setReportDate(LocalDate.now());
            newReport.setReportMessage(reportMessage);
            newReport.setAdopter(adopter);
            reportRepository.save(newReport);
        }
        Report changeReport = reportRepository.getReportByAdopterAdopterIdAndReportDate(adopterId, LocalDate.now());
        changeReport.setReportMessage(reportMessage);
        reportRepository.save(changeReport);
    }

    /**
     * Позволяет получить список всех отчетов за выбранный день с выбранным статусом
     *
     * @param reportDate   дата отчета
     * @param reportStatus статус отчета (ожидает проверки/проверен, принят/проверен, запрошены доработки)
     * @return список отчетов
     */
    public Collection<Report> getAllReportsPerDay(LocalDate reportDate, ReportStatus reportStatus) {
        logger.info("launching the getAllReportPerDay method");
        return reportRepository.findAllByReportDateAndReportStatus(reportDate, reportStatus);
    }

    /**
     * Позволяет получить список всех отчетов за выбранный день
     *
     * @param reportDate дата отчета
     * @return список отчетов
     */
    public Report getReportPerDay(LocalDate reportDate, Long adopterId) {
        logger.info("launching the getReportPerDay method");
        return reportRepository.findByReportDateAndAdopterAdopterId(reportDate, adopterId);
    }


    /**
     * Позволяет получить список отчетов по выбранному усыновителю.
     *
     * @param adopterId идентификатор усыновителя
     * @return список отчетов по выбранному усыновителю.
     */
    public Collection<Report> getReportsByAdopterId(Long adopterId) {
        logger.info("launching the getAllReportPerDay method with adopterId: {}", adopterId);
        return reportRepository.findReportByAdopterAdopterId(adopterId);
    }

    /**
     * Позволяет получить отчет по его Id
     *
     * @param reportId идентификатор отчета (<b>не</b> может быть <b>null</b>)
     * @return отчет
     */
    public Report getReportByReportId(long reportId) {
        logger.info("launching the getAllReportPerDay method with reportId: {}", reportId);
        return reportRepository.findByReportId(reportId);
    }

    /**
     * Позволяет сменить статус отчету (ожидает проверки/проверен, принят/проверен, запрошены доработки)
     *
     * @param reportId     идентификатор отчета (<b>не</b> может быть <b>null</b>)
     * @param reportStatus статус отчета (AWAITING_VERIFICATION / VERIFIED_ACCEPTED/ VERIFIED_REQUESTED_IMPROVEMENTS)
     * @return данные по отчету с измененным статусом
     */
    public Report editReportStatus(Long reportId, ReportStatus reportStatus) {
        logger.info("launching the editReportStatus method with reportId: {}", reportId);
        Report changeReport = reportRepository.findByReportId(reportId);
        changeReport.setReportStatus(reportStatus);
        Long adopterChatId = changeReport.getAdopter().getAdopterChatId();
        if (reportStatus.equals(VERIFIED_ACCEPTED)) {
            SendMessage reportStatusOkMessage = new SendMessage(adopterChatId, "Отчет проверен, принят");
            telegramBot.execute(reportStatusOkMessage);
        }else {
            SendMessage reportStatusOkMessage = new SendMessage(adopterChatId, "Отчет проверен, требуются доработки");
            telegramBot.execute(reportStatusOkMessage);
        }
        return reportRepository.save(changeReport);
    }

    /**
     * Позволяет добавить / сменить фото в отчете
     *
     * @param adopterChatId идентификатор чата усыновителя (<b>не</b> может быть <b>null</b>)
     * @param photoSize     фото
     * @return false=отчет не найден / true=отчет найден, фото добавлено
     */
    public boolean editReportPhoto(Long adopterChatId, String photoSize) {
        logger.info("launching the editReportPhoto method with adopterChatId: {}", adopterChatId);
        Report changeReport = reportRepository.getReportByAdopterAdopterChatIdAndReportDate(adopterChatId, LocalDate.now());
        if (changeReport == null || photoSize == null) {
            return false;
        }
        changeReport.setReportPhotoSize(photoSize);
        reportRepository.save(changeReport);
        return true;
    } //(1 день = 1 отчет = 1 фото)
}
