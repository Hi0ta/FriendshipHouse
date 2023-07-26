package sky.pro.friendshiphouse.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sky.pro.friendshiphouse.constant.ReportStatus;
import sky.pro.friendshiphouse.model.Report;
import sky.pro.friendshiphouse.service.ReportService;

import java.time.LocalDate;
import java.util.Collection;

@RestController
@RequestMapping("/report")
public class ReportController {

    private final ReportService reportService;

    public ReportController(ReportService reportService) {this.reportService = reportService;}

    @Operation(
            tags = "Отчет",
            summary = "Список отчетов за выбранный день с выбранным статусом",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "список отчетов"
                    )})
    @GetMapping("per-day/") // GET http://localhost:8080/adopter/per-day/
    public ResponseEntity<Collection<Report>> getAllReportPerDay(@Parameter(name = "reportDate", description = "Дата")
                                                                 @RequestParam("reportDate") LocalDate reportDate,
                                                                 @Parameter(name = "reportStatus", description = "AWAITING_VERIFICATION=ожидает проверки/VERIFIED_ACCEPTED=проверен, принят/VERIFIED_REQUESTED_IMPROVEMENTS=проверен, запрошены доработки")
                                                                 @RequestParam("reportStatus") ReportStatus reportStatus) {
        return ResponseEntity.ok(reportService.getAllReportPerDay(reportDate, reportStatus));
    }

    @Operation(
            tags = "Отчет",
            summary = "Изменение статуса отчета (ожидает проверки/проверен, принят/проверен, запрошены доработки)",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "статус отчета успешно изменен"
                    )})
    @PutMapping("change-status/{reportId}")  // PUT http://localhost:8080/report/change-status/reportId
    public ResponseEntity<Report> editReportStatus(@Parameter(name = "reportId", description = "обязательно правильно заполнить поле <b>reportId</b> (если указать неверно отчет не будет найден в БД)")
                                                   @PathVariable long reportId,
                                                   @Parameter(name = "reportStatus", description = "AWAITING_VERIFICATION=ожидает проверки/VERIFIED_ACCEPTED=проверен, принят/VERIFIED_REQUESTED_IMPROVEMENTS=проверен, запрошены доработки")
                                                   @RequestParam("reportStatus") ReportStatus reportStatus) {
        Report changeReport = reportService.editReportStatus(reportId, reportStatus);
        return ResponseEntity.ok(changeReport);
    }

    @Operation(
            tags = "Отчет",
            summary = "Изменение текста отчета",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Текст отчета успешно изменен"
                    )})
    @PutMapping("change-report-message/{reportId}")  // PUT http://localhost:8080/report/change-report-message/reportId
    public ResponseEntity<Report> editReportMessage(@Parameter(name = "reportId", description = "обязательно правильно заполнить поле <b>reportId</b> (если указать неверно отчет не будет найден в БД)")
                                                    @PathVariable long reportId,
                                                    @Parameter(description = "Текст отчета", name = "reportMessage")
                                                    @RequestParam("reportMessage") String reportMessage) {
        Report changeReport = reportService.editReportMessage(reportId, reportMessage);
        return ResponseEntity.ok(changeReport);
    }

    @Operation(
            tags = "Отчет",
            summary = "Изменение/добавление фото отчета",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "фото у отчета успешно изменено/добавлено"
                    )})
    @PutMapping("change-report-photo/{reportId}")  // PUT http://localhost:8080/report/change-report-photo/reportId
    public ResponseEntity<Report> volunteerEditReportPhoto(@Parameter(name = "reportId", description = "обязательно правильно заполнить поле <b>reportId</b> (если указать неверно отчет не будет найден в БД)")
                                                  @PathVariable long reportId,
                                                  @Parameter(description = "фото", name = "reportPhoto")
                                                  @RequestParam("reportPhoto") byte[] reportPhoto) {
        Report changeReport = reportService.volunteerEditReportPhoto(reportId, reportPhoto);
        return ResponseEntity.ok(changeReport);
    }
}
