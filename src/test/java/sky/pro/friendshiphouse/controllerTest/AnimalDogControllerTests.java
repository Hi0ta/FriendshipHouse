package sky.pro.friendshiphouse.controllerTest;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import sky.pro.friendshiphouse.constant.AnimalDogKind;
import sky.pro.friendshiphouse.controller.AnimalDogController;
import sky.pro.friendshiphouse.model.AnimalDog;
import sky.pro.friendshiphouse.repository.AdopterRepository;
import sky.pro.friendshiphouse.repository.AnimalDogRepository;
import sky.pro.friendshiphouse.repository.VolunteerRepository;
import sky.pro.friendshiphouse.service.AdopterService;
import sky.pro.friendshiphouse.service.AnimalDogService;
import sky.pro.friendshiphouse.service.VolunteerService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static sky.pro.friendshiphouse.constant.AnimalDogKind.PUPPY;

@WebMvcTest
public class AnimalDogControllerTests {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    private AdopterRepository adopterRepository;
    @MockBean
    private AnimalDogRepository animalDogRepository;

    @MockBean
    private VolunteerRepository volunteerRepository;

    @SpyBean
    private AdopterService adopterService;

    @SpyBean
    private AnimalDogService animalDogService;

    @SpyBean
    private VolunteerService volunteerService;


    @Test
    public void animalDogTest() throws Exception {
        final long animalDogId = 1L;
        final String animalDogName = "Van";
        final int animalDogAge = 2;
        final String animalDogBreed = "Van";
        final String animalDogInfo = "Van";
        final AnimalDogKind animalDogKind = PUPPY;
        final boolean animalDogStatusFree = true;

        JSONObject animalDogObject = new JSONObject();
        animalDogObject.put("animalDogId", animalDogId);
        animalDogObject.put("animalDogName", animalDogName);
        animalDogObject.put("animalDogAge", animalDogAge);
        animalDogObject.put("animalDogBreed", animalDogBreed);
        animalDogObject.put("animalDogInfo", animalDogInfo);
        animalDogObject.put("animalDogKind", animalDogKind);
        animalDogObject.put("animalDogStatusFree", animalDogStatusFree);

        AnimalDog animalDog = new AnimalDog();
        animalDog.setAnimalDogId(animalDogId);
        animalDog.setAnimalDogName(animalDogName);
        animalDog.setAnimalDogAge(animalDogAge);
        animalDog.setAnimalDogBreed(animalDogBreed);
        animalDog.setAnimalDogInfo(animalDogInfo);
        animalDog.setAnimalDogKind(animalDogKind);
        animalDog.setAnimalDogStatusFree(animalDogStatusFree);


        when(animalDogRepository.save(any(AnimalDog.class))).thenReturn(animalDog);
        when(animalDogRepository.findByAnimalDogId(any(Long.class))).thenReturn(animalDog);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/dog/")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/dog/" + animalDogId)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.animalDogId").value(animalDogId))
                .andExpect(jsonPath("$.animalDogName").value(animalDogName))
                .andExpect(jsonPath("$.animalDogAge").value(animalDogAge))
                .andExpect(jsonPath("$.animalDogBreed").value(animalDogBreed))
                .andExpect(jsonPath("$.animalDogInfo").value(animalDogInfo))
                //               .andExpect(jsonPath("$.animalDogKind").value(animalDogKind))
                .andExpect(jsonPath("$.animalDogStatusFree").value(animalDogStatusFree));

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/dog/")
                        .content(animalDogObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.animalDogId").value(animalDogId))
                .andExpect(jsonPath("$.animalDogName").value(animalDogName))
                .andExpect(jsonPath("$.animalDogAge").value(animalDogAge))
                .andExpect(jsonPath("$.animalDogBreed").value(animalDogBreed))
                .andExpect(jsonPath("$.animalDogInfo").value(animalDogInfo))
//                .andExpect(jsonPath("$.animalDogKind").value(animalDogKind))
                .andExpect(jsonPath("$.animalDogStatusFree").value(animalDogStatusFree));

        mockMvc.perform(MockMvcRequestBuilders
                        .put("/dog/")
                        .content(animalDogObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.animalDogId").value(animalDogId))
                .andExpect(jsonPath("$.animalDogName").value(animalDogName))
                .andExpect(jsonPath("$.animalDogAge").value(animalDogAge))
                .andExpect(jsonPath("$.animalDogBreed").value(animalDogBreed))
                .andExpect(jsonPath("$.animalDogInfo").value(animalDogInfo))
                //               .andExpect(jsonPath("$.animalDogKind").value(animalDogKind))
                .andExpect(jsonPath("$.animalDogStatusFree").value(animalDogStatusFree));

        mockMvc.perform(MockMvcRequestBuilders
                        .put("/dog/change-status/" + animalDogId + "?animalDogStatusFree=" + animalDogStatusFree)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/dog/" + animalDogId)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }


    @Test
    public void animalDogBadRequestTest() throws Exception {

        final long animalDogId = 1L;
        final String animalDogName = "Van";
        final int animalDogAge = 2;
        final String animalDogBreed = "Van";
        final String animalDogInfo = "Van";
        final AnimalDogKind animalDogKind = PUPPY;
        final boolean animalDogStatusFree = true;

        JSONObject animalDogObject = new JSONObject();
        animalDogObject.put("animalDogId", animalDogId);
        animalDogObject.put("animalDogName", animalDogName);
        animalDogObject.put("animalDogAge", animalDogAge);
        animalDogObject.put("animalDogBreed", animalDogBreed);
        animalDogObject.put("animalDogInfo", animalDogInfo);
        animalDogObject.put("animalDogKind", animalDogKind);
        animalDogObject.put("animalDogStatusFree", animalDogStatusFree);

        AnimalDog animalDog = new AnimalDog();
        animalDog.setAnimalDogId(animalDogId);
        animalDog.setAnimalDogName(animalDogName);
        animalDog.setAnimalDogAge(animalDogAge);
        animalDog.setAnimalDogBreed(animalDogBreed);
        animalDog.setAnimalDogInfo(animalDogInfo);
        animalDog.setAnimalDogKind(animalDogKind);
        animalDog.setAnimalDogStatusFree(animalDogStatusFree);

        final List<AnimalDog> animalDogs = new ArrayList<>();
        animalDogs.add(animalDog);

        when(animalDogRepository.findAll()).thenReturn(animalDogs);
        when(animalDogRepository.findByAnimalDogId(any(Long.class))).thenReturn(null);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/dog/" + animalDogId))
                .andExpect(status().isBadRequest());

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/dog/")
                        .content(animalDogObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }
}
