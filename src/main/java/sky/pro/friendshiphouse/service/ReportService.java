package sky.pro.friendshiphouse.service;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import sky.pro.friendshiphouse.constant.ReportStatus;
import sky.pro.friendshiphouse.exception.ObjectAbsenceException;
import sky.pro.friendshiphouse.model.Adopter;
import sky.pro.friendshiphouse.model.Report;
import sky.pro.friendshiphouse.repository.ReportRepository;

import java.time.LocalDate;
import java.util.Collection;

import static sky.pro.friendshiphouse.constant.ReportStatus.AWAITING_VERIFICATION;


@RequiredArgsConstructor
@Service
public class ReportService {

// Каждый день волонтеры просматривают все присланные отчеты после 21:00. - реализовано через swagger
    private final ReportRepository reportRepository;
    private final Logger logger = LoggerFactory.getLogger(ReportService.class);


    /**
     * Позволяет добавить отчет в БД
     */
    public void createReport(String reportMessage, Adopter adopter) {
        logger.info("launching the createReport method");
        Long adopterId = adopter.getAdopterId();
        if(reportRepository.getReportByAdopter_AdopterIdAndReportDate(adopterId,LocalDate.now()) == null) {
            Report newReport = new Report();
            newReport.setReportStatus(AWAITING_VERIFICATION);
            newReport.setReportDate(LocalDate.now());
            newReport.setReportMessage(reportMessage);
            newReport.setAdopter(adopter);
            reportRepository.save(newReport);
        }
        Report changeReport = reportRepository.getReportByAdopter_AdopterIdAndReportDate(adopterId, LocalDate.now());
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
        logger.info("launching the getAllReportPerDay method with adopterId: {}", adopterId);
        return reportRepository.findReportByAdopter_AdopterId(adopterId);
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
        Report changeReport = reportRepository.getByReportId(reportId);
        if (changeReport == null) {
            throw new ObjectAbsenceException("Отчет с таким reportId не найден в БД");
        }
        changeReport.setReportStatus(reportStatus);
        return reportRepository.save(changeReport);
    }

    /**
     * Позволяет добавить / сменить фото в отчете
     *
     * @param adopterChatId    идентификатор чата усыновителя (<b>не</b> может быть <b>null</b>)
     * @param photoSize фото
     * @return false=отчет не найден / true=отчет найден, фото добавлено
     */
    public boolean editReportPhoto(Long adopterChatId, String photoSize) {
        logger.info("launching the editReportPhoto method with volunteerId: {}", adopterChatId);
        Report changeReport = reportRepository.getReportByAdopter_AdopterChatIdAndReportDate(adopterChatId, LocalDate.now());
        if (changeReport == null || photoSize == null) {
           return false;
        }
        changeReport.setReportPhotoSize(photoSize);
        reportRepository.save(changeReport);
        return true;
    } //(1 день = 1 отчет = 1 фото)
}
