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
import sky.pro.friendshiphouse.model.Adopter;
import sky.pro.friendshiphouse.service.AdopterService;

import java.util.Collection;


@RestController
@RequestMapping("/adopter")
public class AdopterController {

    private final AdopterService adopterService;

    public AdopterController(AdopterService adopterService) {
        this.adopterService = adopterService;
    }

    @ExceptionHandler(value = ObjectAlreadyExistsException.class)
    public ResponseEntity<String> ObjectAlreadyExistsHandler(ObjectAlreadyExistsException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    @ExceptionHandler(value = FormatNotComplianceException.class)
    public ResponseEntity<String> FormatNotComplianceHandler(FormatNotComplianceException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    @ExceptionHandler(value = ObjectAbsenceException.class)
    public ResponseEntity<String> ObjectAbsenceHandler(ObjectAbsenceException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    @Operation(
            tags = "Усыновитель",
            summary = "Список всех усыновителей из БД",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "список всех усыновителей"
                    )
            }
    )
    @GetMapping() // GET http://localhost:8080/adopter/
    public ResponseEntity<Collection<Adopter>> getAllAdopter() {
        return ResponseEntity.ok(adopterService.getAllAdopter());
    }

    @Operation(
            tags = "Усыновитель",
            summary = "Поиск усыновителя в БД по его adopterId",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "усыновитель найден"
                    )
            }
    )
    @GetMapping("{adopterId}") // GET http://localhost:8080/adopter/adopterId
    public ResponseEntity<Adopter> getAdopterById(@Parameter(name = "adopterId", description = "обязательно правильно заполнить <b>adopterId</b> <br/>(если указать неверно усыновитель не будет найден в БД)")
                                                  @PathVariable long adopterId) {
        return ResponseEntity.ok(adopterService.getAdopterById(adopterId));
    }

    @Operation(
            tags = "Усыновитель",
            summary = "Добавление усыновителя в БД",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "необходимо заполнить все поля кроме <b>adopterId</b> (проставляется автоматически БД) <br> поле <b>adopterChatId</b> должно состоять из 10 цифр"
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "усыновитель добавлен в БД",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = Adopter[].class)
                            )
                    )
            }
    )
    @PostMapping()   //  POST http://localhost:8080/adopter/
    public ResponseEntity<Adopter> createAdopter(@RequestBody Adopter newAdopter) {
        adopterService.createAdopter(newAdopter);
        return ResponseEntity.ok(newAdopter);
    }

    @Operation(
            tags = "Усыновитель",
            summary = "Внесение изменений в БД по усыновителю",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "необходимо правильно заполнить поле <b>adopterId</b> (если указать неверно усыновитель не будет найден в БД) <br> поле <b>adopterChatId</b> должно состоять из 10 цифр"
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "данные по усыновителю изменены",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = Adopter[].class)
                            )
                    )
            })
    @PutMapping()    //  PUT http://localhost:8080/adopter/
    public ResponseEntity<Adopter> editAdopter(@RequestBody Adopter adopter) {
        adopterService.editAdopter(adopter);
        return ResponseEntity.ok(adopter);
    }

    @Operation(
            tags = "Усыновитель",
            summary = "Удаление усыновителя из БД",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "усыновитель удален из БД"
                    )
            })
    @DeleteMapping("{adopterId}")  // DELETE http://localhost:8080/adopter/adopterId
    public ResponseEntity deleteAdopter(@Parameter(name = "adopterId", description = "обязательно правильно заполнить <b>adopterId</b> <br/>(если указать неверно усыновитель не будет найден в БД)")
                                        @PathVariable long adopterId) {
        adopterService.deleteAdopter(adopterId);
        return ResponseEntity.ok().build();
    }
}
