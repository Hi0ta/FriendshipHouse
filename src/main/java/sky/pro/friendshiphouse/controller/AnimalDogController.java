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
import sky.pro.friendshiphouse.model.AnimalDog;
import sky.pro.friendshiphouse.service.AnimalDogService;

import java.util.Collection;

@RestController
@RequestMapping("/dog")
public class AnimalDogController {


    private final AnimalDogService animalDogService;

    public AnimalDogController(AnimalDogService animalDogService) {
        this.animalDogService = animalDogService;
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
            tags = "Собака",
            summary = "Список всех собак из БД",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "список всех собак"
                    )
            }
    )
    @GetMapping() // GET http://localhost:8080/dog/
    public ResponseEntity<Collection<AnimalDog>> getAllAnimalDog() {
        return ResponseEntity.ok(animalDogService.getAllAnimalDog());
    }

    @Operation(
            tags = "Собака",
            summary = "Поиск собаки в БД по ее animalDogId",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "собака найдена"
                    )
            }
    )
    @GetMapping("{animalDogId}") // GET http://localhost:8080/dog/animalDogId
    public ResponseEntity<AnimalDog> getAnimalDogById(@Parameter(name = "animalDogId", description = "обязательно правильно заполнить <b>номер animalDogId</b> <br/>(если указать неверно собака не будет найдена в БД)")
                                                      @PathVariable long animalDogId) {
        return ResponseEntity.ok(animalDogService.getAnimalDogById(animalDogId));
    }

    @Operation(
            tags = "Собака",
            summary = "Добавление собаки в БД",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "обязательно заполнить все поля кроме <b>animalDogId</b> (проставляется автоматически БД)"
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "собакa добавленa в БД",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = AnimalDog[].class)
                            )
                    )
            }
    )
    @PostMapping() //  POST http://localhost:8080/dog/
    public ResponseEntity<AnimalDog> createAnimalDog(@RequestBody AnimalDog animalDog) {
        animalDogService.createAnimalDog(animalDog);
        return ResponseEntity.ok(animalDog);
    }

    @Operation(
            tags = "Собака",
            summary = "Внесение изменений в БД по собаке",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "обязательно правильно заполнить поле <b>animalDogId</b> (если указать неверно собака не будет найдена в БД )"
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "данные по собаке изменены",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = AnimalDog[].class)
                            )
                    )
            })
    @PutMapping()   //  PUT http://localhost:8080/dog/
    public ResponseEntity<AnimalDog> editAnimalDog(@RequestBody AnimalDog animalDog) {
        animalDogService.editAnimalDog(animalDog);
        return ResponseEntity.ok(animalDog);
    }

    @Operation(
            tags = "Собака",
            summary = "Изменение статуса у собаки (занята/свободна)",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "статус собаки успешно изменен"
                    )
            })
    @PutMapping("change-status/{animalDogId}")  // PUT http://localhost:8080/dog/change-status/animalDogId
    public ResponseEntity<AnimalDog> editAnimalDogStatus(@Parameter(name = "animalDogId", description = "обязательно правильно заполнить поле <b>animalDogId</b> (если указать неверно собака не будет найдена в БД и изменения вносить будет некуда)")
                                                         @PathVariable long animalDogId,
                                                         @Parameter(description = "true=свободна / false=занята", name = "animalDogStatusFree")
                                                         @RequestParam("animalDogStatusFree") boolean animalDogStatusFree) {
        AnimalDog changeAnimalDog = animalDogService.editAnimalDogStatus(animalDogId, animalDogStatusFree);
        return ResponseEntity.ok(changeAnimalDog);
    }

    @Operation(
            tags = "Собака",
            summary = "Удаление собаки из БД",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "собака удалена из БД"
                    )
            })
    @DeleteMapping("{animalDogId}")  // DELETE http://localhost:8080/dog/animalDogId
    public ResponseEntity deleteAnimalDog(@Parameter(name = "animalDogId", description = "обязательно правильно заполнить <b>animalDogId</b> <br/>(если указать неверно собака не будет найдена в БД)")
                                          @PathVariable long animalDogId) {
        animalDogService.deleteAnimalDog(animalDogId);
        return ResponseEntity.ok().build();
    }
}
