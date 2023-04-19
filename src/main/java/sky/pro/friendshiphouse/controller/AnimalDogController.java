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
import sky.pro.friendshiphouse.model.AnimalDog;
import sky.pro.friendshiphouse.service.AnimalDogService;

@RestController
@RequestMapping("dog/")
public class AnimalDogController {

    @Autowired
    private final AnimalDogService animalDogService;


    public AnimalDogController(AnimalDogService animalDogService) {
        this.animalDogService = animalDogService;
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
                            description = "данные по собаке добавлены в БД",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = AnimalDog[].class)
                            )
                    )
            }
    )
    @PostMapping() //  POST http://localhost:8080/dogs/
    public AnimalDog createAnimalDog(@RequestBody AnimalDog animalDog) {
        return animalDogService.createAnimalDog(animalDog);
    }

    @Operation(
            tags = "Собака",
            summary = "Внесение изменений в БД по собаке",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "обязательно правильно заполнить поле <b>animalDogId</b> (если указать неверно собака не будет найдена в БД и изменения вносить будет некуда)"
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
    @PutMapping()   //  PUT http://localhost:8080/dogs/
    public ResponseEntity<AnimalDog> editAnimalDog(@RequestBody AnimalDog animalDog) {
        AnimalDog foundAnimalDog = animalDogService.editAnimalDog(animalDog);
        if (foundAnimalDog == null) {
            ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.ok(foundAnimalDog);
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
    @DeleteMapping("{id}")  // DELETE http://localhost:8080/dogs/id
    public ResponseEntity deleteAnimalDog(@Parameter(name = "номер идентификатора", description = "обязательно правильно заполнить <b>номер идентификатора</b> <br/>(если указать неверно собака не будет найдена в БД)")
                                          @PathVariable long id) {
        animalDogService.deleteAnimalDog(id);
        return ResponseEntity.ok().build();
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
    @PutMapping("change-status/{id}")  // PUT http://localhost:8080/dogs/change-status/id
    public ResponseEntity<AnimalDog> editAnimalDogStatus(@Parameter(name = "номер идентификатора", description = "обязательно правильно заполнить поле <b>animalDogId</b> (если указать неверно собака не будет найдена в БД и изменения вносить будет некуда)")
                                                         @PathVariable long id,
                                                         @Parameter(description = "true=свободна / false=занята", name = "cтатус")
                                                         @RequestParam boolean status) {
        return ResponseEntity.ok(animalDogService.editAnimalDogStatus(id, status));
    }
}
