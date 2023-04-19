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
import sky.pro.friendshiphouse.model.Adopter;
import sky.pro.friendshiphouse.service.AdopterService;


@RestController
@RequestMapping("adopter/")
public class AdopterController {
    @Autowired
    private final AdopterService adopterService;

    public AdopterController(AdopterService adopterService) {
        this.adopterService = adopterService;
    }

    @Operation(
            tags = "Усыновитель",
            summary = "Добавление усыновителя в БД",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "обязательно заполнить все поля кроме <b>adopterId</b> (проставляется автоматически БД) <br> поле <b>adopterChatId</b> должно состоять из 10 цифр"
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "данные по усыновителю добавлены в БД",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = Adopter[].class)
                                    //    examples = @ExampleObject()
                            )
                    )
            }
    )
    @PostMapping()   //  POST http://localhost:8080/adopter/
    public Adopter createAdopter(@RequestBody Adopter adopter) {
        return adopterService.createAdopter(adopter);
    }

    @Operation(
            tags = "Усыновитель",
            summary = "Внесение изменений в БД по усыновителю",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "обязательно правильно заполнить поле <b>adopterId</b> (если указать неверно усыновитель не будет найден в БД и изменения вносить будет некуда) <br> поле <b>adopterChatId</b> должно состоять из 10 цифр"
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
        Adopter foundAdopter = adopterService.editAdopter(adopter);
        if (foundAdopter == null) {
            ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.ok(foundAdopter);
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
    @DeleteMapping("{id}")  // DELETE http://localhost:8080/adopter/id
    public ResponseEntity deleteAdopter(@Parameter(name = "номер идентификатора", description = "обязательно правильно заполнить <b>номер идентификатора</b> <br/>(если указать неверно усыновитель не будет найден в БД)")
                                        @PathVariable long id) {
        adopterService.deleteAdopter(id);
        return ResponseEntity.ok().build();
    }
}
