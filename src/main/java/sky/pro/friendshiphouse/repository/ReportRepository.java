package sky.pro.friendshiphouse.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sky.pro.friendshiphouse.constant.ReportStatus;
import sky.pro.friendshiphouse.model.Report;

import java.time.LocalDate;
import java.util.Collection;

@Repository
public interface ReportRepository extends JpaRepository<Report, Long> {
    Report getByReportId(Long reportId);
    Report getReportByAdopter_AdopterIdAndReportDate(Long adopterId, LocalDate date);
    Report getReportByAdopter_AdopterChatIdAndReportDate(Long adopterChatId, LocalDate date);
    Collection<Report> findReportByAdopter_AdopterId(Long adopterId);
    Collection<Report> findReportByReportDateAndReportStatus(LocalDate reportDate, ReportStatus reportStatus);
}
