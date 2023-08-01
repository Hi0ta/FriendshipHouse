package sky.pro.friendshiphouse.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sky.pro.friendshiphouse.exception.FormatNotComplianceException;
import sky.pro.friendshiphouse.exception.ObjectAbsenceException;
import sky.pro.friendshiphouse.exception.ObjectAlreadyExistsException;
import sky.pro.friendshiphouse.model.Volunteer;
import sky.pro.friendshiphouse.service.VolunteerService;

import java.util.Collection;

@RestController
@RequestMapping("/volunteer")
public class VolunteerController {
    private final VolunteerService volunteerService;

    public VolunteerController(VolunteerService volunteerService) {
        this.volunteerService = volunteerService;
    }

    @ExceptionHandler(value = ObjectAlreadyExistsException.class)
    public ResponseEntity<String> objectAlreadyExistsHandler(ObjectAlreadyExistsException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    @ExceptionHandler(value = FormatNotComplianceException.class)
    public ResponseEntity<String> formatNotComplianceHandler(FormatNotComplianceException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    @ExceptionHandler(value = ObjectAbsenceException.class)
    public ResponseEntity<String> objectAbsenceHandler(ObjectAbsenceException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    @Operation(
            tags = "Волонтер",
            summary = "Список всех волонтеров из БД",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "список всех волонтеров",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = Volunteer[].class)))})
    @GetMapping // GET http://localhost:8080/volunteer
    public ResponseEntity<Collection<Volunteer>> getAllVolunteer() {
        return ResponseEntity.ok(volunteerService.getAllVolunteer());
    }

    @Operation(
            tags = "Волонтер",
            summary = "Поиск волонтера в БД по его volunteerId",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "волонтер найден",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = Volunteer.class)))})
    @GetMapping("/{volunteerId}") // GET http://localhost:8080/volunteer/volunteerId
    public ResponseEntity<Volunteer> getVolunteerById(@Parameter(name = "volunteerId", description = "обязательно правильно заполнить <b>volunteerId</b> <br/>(если указать неверно волонтер не будет найден в БД)")
                                                      @PathVariable long volunteerId) {
        return ResponseEntity.ok(volunteerService.getVolunteerById(volunteerId));
    }


    @Operation(
            tags = "Волонтер",
            summary = "Добавление волонтера в БД",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "обязательно заполнить все поля кроме <b>volunteerId</b> (проставляется автоматически БД) <br> поле <b>volunteerChatId</b> должно состоять из 10 цифр"),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "волонтер добавлен в БД",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = Volunteer.class)))})
    @PostMapping //  POST http://localhost:8080/volunteer
    public ResponseEntity<Volunteer> createVolunteer(@RequestBody Volunteer newVolunteer) {
        volunteerService.createVolunteer(newVolunteer);
        return ResponseEntity.ok(newVolunteer);
    }

    @Operation(
            tags = "Волонтер",
            summary = "Внесение изменений в БД по волонтеру",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "необходимо правильно заполнить поле <b>volunteerId</b> (если указать неверно волонтер не будет найден в БД) <br> поле <b>volunteerChatId</b> должно состоять из 10 цифр"),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "данные по волонтеру изменены",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = Volunteer.class)))})
    @PutMapping   //  PUT http://localhost:8080/volunteer
    public ResponseEntity<Volunteer> editVolunteer(@RequestBody Volunteer volunteer) {
        volunteerService.editVolunteer(volunteer);
        return ResponseEntity.ok(volunteer);
    }

    @Operation(
            tags = "Волонтер",
            summary = "Изменение статуса волонтера (занят/свободен)",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "статус волонтера успешно изменен",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = Volunteer.class)))})
    @PutMapping("/change-status/{volunteerId}")  // PUT http://localhost:8080/volunteer/change-status/volunteerId
    public ResponseEntity<Volunteer> editVolunteerStatus(@Parameter(name = "volunteerId", description = "обязательно правильно заполнить поле <b>volunteerId</b> (если указать неверно волонтер не будет найден в БД)")
                                                         @PathVariable long volunteerId,
                                                         @Parameter(description = "true=свободен / false=занят", name = "volunteerStatusFree")
                                                         @RequestParam("volunteerStatusFree") boolean volunteerStatusFree) {
        Volunteer changeVolunteer = volunteerService.editVolunteerStatus(volunteerId, volunteerStatusFree);
        return ResponseEntity.ok(changeVolunteer);
    }

    @Operation(
            tags = "Волонтер",
            summary = "Удаление волонтера из БД",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "волонтер удален из БД")})
    @DeleteMapping("/{volunteerId}")  // DELETE http://localhost:8080/volunteer/volunteerId
    public ResponseEntity deleteVolunteer(@Parameter(name = "volunteerId", description = "обязательно правильно заполнить <b>volunteerId</b> <br/>(если указать неверно волонтер не будет найден в БД)")
                                          @PathVariable long volunteerId) {
        volunteerService.deleteVolunteer(volunteerId);
        return ResponseEntity.ok().build();
    }
}

