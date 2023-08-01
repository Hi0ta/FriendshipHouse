package sky.pro.friendshiphouse.model;

import lombok.*;
import sky.pro.friendshiphouse.constant.ReportStatus;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Getter
@Setter
public class Report {

    @Id
    @GeneratedValue
    private long reportId;
    private String reportPhotoSize;
    private String reportMessage;
    private LocalDate reportDate;
    @Enumerated(EnumType.STRING)
    private ReportStatus reportStatus; // ожидает проверки; проверен принят; проверен запрошены доработки (ENUM)
    @ManyToOne
    @JoinColumn(name = "adopter_id")
    private Adopter adopter;

    public Report() {
    }

    public Report(String reportPhotoSize, String reportMessage, LocalDate reportDate, ReportStatus reportStatus) {
        this.reportPhotoSize = reportPhotoSize;
        this.reportMessage = reportMessage;
        this.reportDate = reportDate;
        this.reportStatus = reportStatus;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Report report = (Report) o;
        return reportId == report.reportId && reportPhotoSize.equals(report.reportPhotoSize) && reportMessage.equals(report.reportMessage) && reportDate.equals(report.reportDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(reportId, reportPhotoSize, reportMessage, reportDate);
    }

    @Override
    public String toString() {
        return "Отчет " +
                "Id: " + reportId +
                ", PhotoSize: " + reportPhotoSize +
                ", Текст: " + reportMessage +
                ", Дата: " + reportDate +
                ", Статус: " + reportStatus.getDescription() +
                ", Усыновитель Id: " + adopter.getAdopterId();
    }
}


