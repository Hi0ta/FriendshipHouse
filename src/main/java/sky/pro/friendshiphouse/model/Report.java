package sky.pro.friendshiphouse.model;

import sky.pro.friendshiphouse.constant.ReportStatus;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Objects;

@Entity
public class Report {

    @Id
    @GeneratedValue
    private long reportId;
    private byte[] reportPhoto;
    private String reportMessage;
    private LocalDate reportDate;
    private ReportStatus reportStatus; // ожидает проверки; проверен принят; проверен запрошены доработки (ENUM)
    @ManyToOne
    @JoinColumn(name = "adopter_id")
    private Adopter adopter;


    public long getReportId() {
        return reportId;
    }

    public void setReportId(long reportId) {
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

    public ReportStatus getReportStatus() {return reportStatus;}

    public void setReportStatus(ReportStatus reportStatus) {this.reportStatus = reportStatus;}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Report report = (Report) o;
        return reportId == report.reportId && Arrays.equals(reportPhoto, report.reportPhoto) && Objects.equals(reportMessage, report.reportMessage) && Objects.equals(reportDate, report.reportDate) && Objects.equals(reportStatus, report.reportStatus) && Objects.equals(adopter, report.adopter);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(reportId, reportMessage, reportDate, reportStatus, adopter);
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
                ", reportStatus=" + reportStatus +
                '}';
    }
}
