package sky.pro.friendshiphouse.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sky.pro.friendshiphouse.model.Volunteer;
import sky.pro.friendshiphouse.service.VolunteerService;

@RestController
@RequestMapping("volunteer/")
public class VolunteerController {

    @Autowired
    private final VolunteerService volunteerService;


    public VolunteerController(VolunteerService volunteerService) {
        this.volunteerService = volunteerService;
    }

    @Operation(
            tags = "Волонтер",
            summary = "Добавление волонтера в БД",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "обязательно заполнить все поля кроме <b>volunteerId</b> (проставляется автоматически БД) <br> поле <b>volunteerChatId</b> должно состоять из 10 цифр"
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "данные по волонтеру добавлены в БД",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = Volunteer[].class)
                            )
                    )
            }
    )
    @PostMapping() //  POST http://localhost:8080/volunteers/
    public Volunteer createVolunteer(@RequestBody Volunteer volunteer) {
        return volunteerService.createVolunteer(volunteer);
    }

    @Operation(
            tags = "Волонтер",
            summary = "Внесение изменений в БД по волонтеру",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "обязательно правильно заполнить поле <b>volunteerId</b> (если указать неверно волонтер не будет найден в БД и изменения вносить будет некуда) <br> поле <b>volunteerChatId</b> должно состоять из 10 цифр"
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "данные по волонтеру изменены",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = Volunteer[].class)
                            )
                    )
            })
    @PutMapping()   //  PUT http://localhost:8080/volunteers/
    public ResponseEntity<Volunteer> editVolunteer(@RequestBody Volunteer volunteer) {
        Volunteer foundVolunteer = volunteerService.editVolunteer(volunteer);
        if (foundVolunteer == null) {
            ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.ok(foundVolunteer);
    }

    @Operation(
            tags = "Волонтер",
            summary = "Удаление волонтера из БД",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "волонтер удален из БД"
                    )
            })
    @DeleteMapping("{id}")  // DELETE http://localhost:8080/volunteers/id
    public ResponseEntity deleteVolunteer(@Parameter(name = "номер идентификатора", description = "обязательно правильно заполнить <b>номер идентификатора</b> <br/>(если указать неверно волонтер не будет найден в БД)")
                                          @PathVariable long id) {
        volunteerService.deleteVolunteer(id);
        return ResponseEntity.ok().build();
    }

    @Operation(
            tags = "Волонтер",
            summary = "Изменение статуса волонтера (занят/свободен)",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "статус волонтера успешно изменен"
                    )
            })
    @PutMapping("change-status/{id}")  // PUT http://localhost:8080/volunteers/change-status/id
    public ResponseEntity<Volunteer> editVolunteerStatus(@Parameter(name = "номер идентификатора", description = "обязательно правильно заполнить поле <b>volunteerId</b> (если указать неверно волонтер не будет найден в БД)")
                                                         @PathVariable long id,
                                                         @Parameter(description = "true=свободен / false=занят", name = "cтатус")
                                                         @RequestParam boolean status) {
        return ResponseEntity.ok(volunteerService.editVolunteerStatus(id, status));
    }
}

