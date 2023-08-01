package sky.pro.friendshiphouse.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sky.pro.friendshiphouse.constant.ReportStatus;
import sky.pro.friendshiphouse.model.Report;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ReportRepository extends JpaRepository<Report, Long> {
    Report findByReportId(long reportId);

    Report getReportByAdopterAdopterIdAndReportDate(long adopterId, LocalDate date);

    Report getReportByAdopterAdopterChatIdAndReportDate(long adopterChatId, LocalDate date);

    List<Report> findReportByAdopterAdopterId(long adopterId);

    List<Report> findAllByReportDateAndReportStatus(LocalDate reportDate, ReportStatus reportStatus);

    Report findByReportDateAndAdopterAdopterId(LocalDate reportDate, Long adopterId);
}
