package sky.pro.friendshiphouse.controller;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.request.GetFile;
import com.pengrad.telegrambot.response.GetFileResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import sky.pro.friendshiphouse.constant.ReportStatus;
import sky.pro.friendshiphouse.model.Report;
import sky.pro.friendshiphouse.service.ReportService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.Collection;
import java.util.UUID;

import static org.springframework.http.MediaType.IMAGE_JPEG;

@RestController
@RequestMapping("/report")
public class ReportController {
    private final ReportService reportService;
    private final TelegramBot telegramBot;

    public ReportController(ReportService reportService, TelegramBot telegramBot) {
        this.reportService = reportService;
        this.telegramBot = telegramBot;
    }

    @Operation(
            tags = "Отчет",
            summary = "Список отчетов по выбранному усыновителю",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "список отчетов по выбранному усыновителю",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = Report[].class)))})
    @GetMapping("by-adopterId/{adopterId}") // GET http://localhost:8080/report/adopterId
    public ResponseEntity<Collection<Report>> getReportsByAdopterId(@Parameter(name = "adopterId", description = "обязательно правильно заполнить <b>номер adopterId</b> <br/>(если указать неверно отчет не будет найден в БД)")
                                                                    @PathVariable long adopterId) {
        return ResponseEntity.ok(reportService.getReportsByAdopterId(adopterId));
    }

    @Operation(
            tags = "Отчет",
            summary = "Отчет соответствующий заданному идентификатору",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "отчет найден",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = Report.class)))})
    @GetMapping("by-reportId/{reportId}") //GET http://localhost:8080/report/reportId
    public ResponseEntity<Report> getReportByReportId(@Parameter(name = "reportId", description = "обязательно правильно заполнить поле <b>reportId</b> (если указать неверно отчет не будет найден в БД)")
                                                      @PathVariable long reportId) {
        return ResponseEntity.ok(reportService.getReportByReportId(reportId));
    }

    @Operation(
            tags = "Отчет",
            summary = "Фото к отчету по его reportId",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "фото")})
    @GetMapping("photo/{reportId}")
    public ResponseEntity<byte[]> downloadPhoto(@Parameter(name = "reportId", description = "обязательно правильно заполнить поле <b>reportId</b> (если указать неверно отчет не будет найден в БД)")
                                                @PathVariable long reportId) {
        byte[] photo;
        try {
            photo = telegramBot.getFileContent(telegramBot.execute(new GetFile(reportService.getReportByReportId(reportId).getReportPhotoSize())).file());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(IMAGE_JPEG);
        return ResponseEntity.status(HttpStatus.OK)
                .headers(headers)
                .body(photo);
    }

    @Operation(
            tags = "Отчет",
            summary = "Список отчетов за выбранный день с выбранным статусом",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "список отчетов",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = Report[].class)))})
    @GetMapping("/per-day") // GET http://localhost:8080/report/per-day
    public ResponseEntity<Collection<Report>> getAllReportsPerDay(@Parameter(name = "reportDate", description = "Дата")
                                                                  @RequestParam("reportDate") LocalDate reportDate,
                                                                  @Parameter(name = "reportStatus", description = "AWAITING_VERIFICATION=ожидает проверки/VERIFIED_ACCEPTED=проверен, принят/VERIFIED_REQUESTED_IMPROVEMENTS=проверен, запрошены доработки")
                                                                  @RequestParam("reportStatus") ReportStatus reportStatus) {
        return ResponseEntity.ok(reportService.getAllReportsPerDay(reportDate, reportStatus));
    }

    @Operation(
            tags = "Отчет",
            summary = "Изменение статуса отчета (ожидает проверки/проверен, принят/проверен, запрошены доработки)",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "статус отчета успешно изменен",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = Report.class)))})
    @PutMapping("/change-status/{reportId}")  // PUT http://localhost:8080/report/change-status/reportId
    public ResponseEntity<Report> editReportStatus(@Parameter(name = "reportId", description = "обязательно правильно заполнить поле <b>reportId</b> (если указать неверно отчет не будет найден в БД)")
                                                   @PathVariable long reportId,
                                                   @Parameter(name = "reportStatus", description = "AWAITING_VERIFICATION=ожидает проверки/VERIFIED_ACCEPTED=проверен, принят/VERIFIED_REQUESTED_IMPROVEMENTS=проверен, запрошены доработки")
                                                   @RequestParam("reportStatus") ReportStatus reportStatus) {
        Report changeReport = reportService.editReportStatus(reportId, reportStatus);
        return ResponseEntity.ok(changeReport);
    }
}
