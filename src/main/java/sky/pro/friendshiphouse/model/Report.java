package sky.pro.friendshiphouse.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import sky.pro.friendshiphouse.constant.ReportStatus;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
//@Table(name = "report_dog")
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
}
