package sky.pro.friendshiphouse.model;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Objects;

@Entity
public class Report {

    @Id
    @GeneratedValue
    private Long reportId;
    private byte[] reportPhoto;
    private String reportMessage;
    private LocalDate reportDate;
    private String reportStatus; // ожидает проверки; проверен принят; проверен запрошены доработки - сделать ENUM
    @ManyToOne
    @JoinColumn(name = "adopter_id")
    private Adopter adopter;


    public Long getReportId() {
        return reportId;
    }

    public void setReportId(Long reportId) {
        this.reportId = reportId;
    }

    public byte[] getReportPhoto() {
        return reportPhoto;
    }

    public void setReportPhoto(byte[] reportPhoto) {
        this.reportPhoto = reportPhoto;
    }

    public String getReportMessage() {
        return reportMessage;
    }

    public void setReportMessage(String reportMessage) {
        this.reportMessage = reportMessage;
    }

    public LocalDate getReportDate() {
        return reportDate;
    }

    public void setReportDate(LocalDate reportDate) {
        this.reportDate = reportDate;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Report report = (Report) o;
        return reportId.equals(report.reportId) && Arrays.equals(reportPhoto, report.reportPhoto) && reportMessage.equals(report.reportMessage) && reportDate.equals(report.reportDate);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(reportId, reportMessage, reportDate);
        result = 31 * result + Arrays.hashCode(reportPhoto);
        return result;
    }

    @Override
    public String toString() {
        return "Report{" +
                "reportId=" + reportId +
                ", reportPhoto=" + Arrays.toString(reportPhoto) +
                ", reportMessage='" + reportMessage + '\'' +
                ", reportDate=" + reportDate +
                '}';
    }
}
