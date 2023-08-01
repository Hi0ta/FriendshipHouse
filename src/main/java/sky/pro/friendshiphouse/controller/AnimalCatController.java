package sky.pro.friendshiphouse.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sky.pro.friendshiphouse.exception.ObjectAbsenceException;
import sky.pro.friendshiphouse.exception.ObjectAlreadyExistsException;
import sky.pro.friendshiphouse.model.AnimalCat;
import sky.pro.friendshiphouse.service.AnimalCatService;

import java.util.Collection;

@RestController
@RequestMapping("/cat")
public class AnimalCatController {
    private final AnimalCatService animalCatService;

    public AnimalCatController(AnimalCatService animalCatService) {
        this.animalCatService = animalCatService;
    }

    @ExceptionHandler(value = ObjectAlreadyExistsException.class)
    public ResponseEntity<String> objectAlreadyExistsHandler(ObjectAlreadyExistsException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    @ExceptionHandler(value = ObjectAbsenceException.class)
    public ResponseEntity<String> objectAbsenceHandler(ObjectAbsenceException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    @Operation(
            tags = "Кошка",
            summary = "Список всех кошек из БД",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "список всех кошек",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = AnimalCat[].class)))})
    @GetMapping // GET http://localhost:8080/cat
    public ResponseEntity<Collection<AnimalCat>> getAllAnimalCat() {
        return ResponseEntity.ok(animalCatService.getAllAnimalCat());
    }

    @Operation(
            tags = "Кошка",
            summary = "Список всех кошек из БД с учетом статуса",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "список всех кошек с учетом статуса",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = AnimalCat[].class)))})
    @GetMapping("/statusFree") // GET http://localhost:8080/cat/statusFree
    public ResponseEntity<Collection<AnimalCat>> getAnimalCatByAnimalCatStatusFree(@Parameter(description = "true=свободна / false=занята", name = "animalCatStatusFree")
                                                                                   @RequestParam("animalCatStatusFree") boolean animalCatStatusFree) {
        return ResponseEntity.ok(animalCatService.getAnimalCatByAnimalCatStatusFree(animalCatStatusFree));
    }

    @Operation(
            tags = "Кошка",
            summary = "Поиск кошки в БД по ее animalCatId",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "кошка найдена",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = AnimalCat.class)))})
    @GetMapping("/{animalCatId}") // GET http://localhost:8080/cat/animalCatId
    public ResponseEntity<AnimalCat> getAnimalCatById(@Parameter(name = "animalCatId", description = "обязательно правильно заполнить <b>номер animalCatId</b> <br/>(если указать неверно кошка не будет найдена в БД)")
                                                      @PathVariable long animalCatId) {
        return ResponseEntity.ok(animalCatService.getAnimalCatById(animalCatId));
    }

    @Operation(
            tags = "Кошка",
            summary = "Добавление кошки в БД",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "обязательно заполнить все поля кроме <b>animalCatId</b> (проставляется автоматически БД)"
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "кошкa добавленa в БД",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = AnimalCat.class)))})
    @PostMapping //  POST http://localhost:8080/cat
    public ResponseEntity<AnimalCat> createAnimalCat(@RequestBody AnimalCat animalCat) {
        animalCatService.createAnimalCat(animalCat);
        return ResponseEntity.ok(animalCat);
    }

    @Operation(
            tags = "Кошка",
            summary = "Внесение изменений в БД по кошке",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "обязательно правильно заполнить поле <b>animalCatId</b> (если указать неверно кошка не будет найдена в БД )"
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "данные по кошке изменены",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = AnimalCat.class)))})
    @PutMapping   //  PUT http://localhost:8080/cat
    public ResponseEntity<AnimalCat> editAnimalCat(@RequestBody AnimalCat animalCat) {
        animalCatService.editAnimalCat(animalCat);
        return ResponseEntity.ok(animalCat);
    }

    @Operation(
            tags = "Кошка",
            summary = "Изменение статуса у кошки (занята/свободна)",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "статус кошки успешно изменен",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = AnimalCat.class)))})
    @PutMapping("/change-status/{animalCatId}")  // PUT http://localhost:8080/cat/change-status/animalCatId
    public ResponseEntity<AnimalCat> editAnimalCatStatus(@Parameter(name = "animalCatId", description = "обязательно правильно заполнить поле <b>animalCatId</b> (если указать неверно кошка не будет найдена в БД и изменения вносить будет некуда)")
                                                         @PathVariable long animalCatId,
                                                         @Parameter(description = "true=свободна / false=занята", name = "animalCatStatusFree")
                                                         @RequestParam("animalCatStatusFree") boolean animalCatStatusFree) {
        AnimalCat changeAnimalCat = animalCatService.editAnimalCatStatus(animalCatId, animalCatStatusFree);
        return ResponseEntity.ok(changeAnimalCat); //
    }

    @Operation(
            tags = "Кошка",
            summary = "Удаление кошки из БД",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "кошка удалена из БД")})
    @DeleteMapping("/{animalCatId}")  // DELETE http://localhost:8080/cat/animalCatId
    public ResponseEntity deleteAnimalCat(@Parameter(name = "animalCatId", description = "обязательно правильно заполнить <b>animalCatId</b> <br/>(если указать неверно кошка не будет найдена в БД)")
                                          @PathVariable long animalCatId) {
        animalCatService.deleteAnimalCat(animalCatId);
        return ResponseEntity.ok().build();
    }
}
