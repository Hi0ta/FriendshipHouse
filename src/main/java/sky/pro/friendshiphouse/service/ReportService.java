package sky.pro.friendshiphouse.service;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import sky.pro.friendshiphouse.constant.ReportStatus;
import sky.pro.friendshiphouse.exception.ObjectAbsenceException;
import sky.pro.friendshiphouse.model.Report;
import sky.pro.friendshiphouse.repository.ReportRepository;

import java.time.LocalDate;
import java.util.Collection;


@RequiredArgsConstructor
@Service
public class ReportService {

// Каждый день волонтеры просматривают все присланные отчеты после 21:00. - реализовано через swagger
    private final ReportRepository reportRepository;
    private final Logger logger = LoggerFactory.getLogger(ReportService.class);


    /**
     * Позволяет добавить отчет в БД
     */
    public void createReport(Report newReport) {//TODO добавить проверку - при повторном отчете за день менять текст в уже существующем а не создавать еще один!!!
        logger.info("launching the createReport method");
        reportRepository.save(newReport);
    }

    /**
     * Позволяет получить список всех отчетов за выбранный день с выбранным статусом
     *
     * @param reportDate   дата отчета
     * @param reportStatus статус отчета (ожидает проверки/проверен, принят/проверен, запрошены доработки)
     * @return список отчетов
     */
    public Collection<Report> getAllReportPerDay(LocalDate reportDate, ReportStatus reportStatus) {
        logger.info("launching the getAllReportPerDay method");
        return reportRepository.findReportByReportDateAndReportStatus(reportDate, reportStatus);
    }
    /**
     * Позволяет получить список отчетов по выбранному усыновителю.
     * @param adopterId идентификатор усыновителя
     * @return список отчетов по выбранному усыновителю.
     */
    public Collection<Report> getReportsByAdopterId(Long adopterId){
        logger.info("launching the getAllReportPerDay method");
        return reportRepository.findByAdopter_AdopterId(adopterId);
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
        if (changeReport == null) {
            throw new ObjectAbsenceException("Отчет с таким reportId не найден в БД");
        }
        changeReport.setReportStatus(reportStatus);
        return reportRepository.save(changeReport);
    }

    /**
     * Позволяет сменить reportMessage отчету
     *
     * @param reportId         идентификатор отчета (<b>не</b> может быть <b>null</b>)
     * @param newReportMessage новый reportMessage
     * @return данные по отчету c измененным reportMessage
     */
    public Report editReportMessage(Long reportId, String newReportMessage) {
        logger.info("launching the editReportMessage method with reportId: {}", reportId);
        Report changeReport = reportRepository.findByReportId(reportId);
        if (changeReport == null) {
            throw new ObjectAbsenceException("Отчет с таким reportId не найден в БД");
        }
        changeReport.setReportMessage(newReportMessage);
        return reportRepository.save(changeReport);
    }

    /**
     * Позволяет волонтеру сменить фото в отчете
     *
     * @param reportId    идентификатор отчета (<b>не</b> может быть <b>null</b>)
     * @param reportPhoto фото
     * @return данные по отчету с измененным фото
     */
    public Report volunteerEditReportPhoto(Long reportId, byte[] reportPhoto) {
        logger.info("launching the volunteerEditReportPhoto method with reportId: {}", reportId);
        Report changeReport = reportRepository.findByReportId(reportId);
        if (changeReport == null) {
            throw new ObjectAbsenceException("Отчет с таким reportId не найден в БД");
        }
        changeReport.setReportPhoto(reportPhoto);
        return reportRepository.save(changeReport);
    }

    /**
     * Позволяет добавить / сменить фото в отчете
     *
     * @param adopterChatId    идентификатор чата усыновителя (<b>не</b> может быть <b>null</b>)
     * @param reportPhoto фото
     * @return false=отчет не найден / true=отчет найден, фото добавлено
     */
    public boolean editReportPhoto(Long adopterChatId, byte[] reportPhoto) {
        logger.info("launching the editReportPhoto method with volunteerId: {}", adopterChatId);
        Report changeReport = reportRepository.findByAdopter_AdopterChatIdAndReportDate(adopterChatId, LocalDate.now());
        if (changeReport == null || reportPhoto == null) {
           return false;
        }
        changeReport.setReportPhoto(reportPhoto);
        reportRepository.save(changeReport);
        return true;
    } //(1 день = 1 отчет = 1 фото)
}
