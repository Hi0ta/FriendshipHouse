package sky.pro.friendshiphouse.controllerTest;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import sky.pro.friendshiphouse.model.Adopter;
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

@WebMvcTest
public class AdopterControllerTests {
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
    public void adopterTest() throws Exception {
        final long adopterId = 1L;
        final Long adopterChatId = 1234567890L;
        final String adopterLastname = "Van";
        final String adopterFirstname = "Van";
        final String adopterMiddlename = "Van";
        final String adopterPassport = "Van";
        final String adopterTelNumber = "Van";
        final String adopterAddress = "Van";

        JSONObject adopterObject = new JSONObject();
        adopterObject.put("adopterId", adopterId);
        adopterObject.put("adopterChatId", adopterChatId);
        adopterObject.put("adopterLastname", adopterLastname);
        adopterObject.put("adopterFirstname", adopterFirstname);
        adopterObject.put("adopterMiddlename", adopterMiddlename);
        adopterObject.put("adopterPassport", adopterPassport);
        adopterObject.put("adopterTelNumber", adopterTelNumber);
        adopterObject.put("adopterAddress", adopterAddress);

        Adopter adopter = new Adopter();
        adopter.setAdopterId(adopterId);
        adopter.setAdopterChatId(adopterChatId);
        adopter.setAdopterLastname(adopterLastname);
        adopter.setAdopterFirstname(adopterFirstname);
        adopter.setAdopterMiddleName(adopterMiddlename);
        adopter.setAdopterPassport(adopterPassport);
        adopter.setAdopterTelNumber(adopterTelNumber);
        adopter.setAdopterAddress(adopterAddress);


        when(adopterRepository.save(any(Adopter.class))).thenReturn(adopter);
        when(adopterRepository.findByAdopterId(any(Long.class))).thenReturn(adopter);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/adopter/")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/adopter/" + adopterId)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.adopterId").value(adopterId))
                .andExpect(jsonPath("$.adopterChatId").value(adopterChatId))
                .andExpect(jsonPath("$.adopterLastname").value(adopterLastname))
                .andExpect(jsonPath("$.adopterFirstname").value(adopterFirstname))
                .andExpect(jsonPath("$.adopterMiddlename").value(adopterMiddlename))
                .andExpect(jsonPath("$.adopterPassport").value(adopterPassport))
                .andExpect(jsonPath("$.adopterTelNumber").value(adopterTelNumber))
                .andExpect(jsonPath("$.adopterAddress").value(adopterAddress));

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/adopter/")
                        .content(adopterObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.adopterId").value(adopterId))
                .andExpect(jsonPath("$.adopterChatId").value(adopterChatId))
                .andExpect(jsonPath("$.adopterLastname").value(adopterLastname))
                .andExpect(jsonPath("$.adopterFirstname").value(adopterFirstname))
                .andExpect(jsonPath("$.adopterMiddlename").value(adopterMiddlename))
                .andExpect(jsonPath("$.adopterPassport").value(adopterPassport))
                .andExpect(jsonPath("$.adopterTelNumber").value(adopterTelNumber))
                .andExpect(jsonPath("$.adopterAddress").value(adopterAddress));

        mockMvc.perform(MockMvcRequestBuilders
                        .put("/adopter/")
                        .content(adopterObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.adopterId").value(adopterId))
                .andExpect(jsonPath("$.adopterChatId").value(adopterChatId))
                .andExpect(jsonPath("$.adopterLastname").value(adopterLastname))
                .andExpect(jsonPath("$.adopterFirstname").value(adopterFirstname))
                .andExpect(jsonPath("$.adopterMiddlename").value(adopterMiddlename))
                .andExpect(jsonPath("$.adopterPassport").value(adopterPassport))
                .andExpect(jsonPath("$.adopterTelNumber").value(adopterTelNumber))
                .andExpect(jsonPath("$.adopterAddress").value(adopterAddress));

        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/adopter/" + adopterId)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void adopterBadRequestTest() throws Exception {
        final long adopterId = 1L;
        final Long adopterChatId = 1234567890L;
        final String adopterLastname = "Van";
        final String adopterFirstname = "Van";
        final String adopterMiddlename = "Van";
        final String adopterPassport = "Van";
        final String adopterTelNumber = "Van";
        final String adopterAddress = "Van";

        JSONObject adopterObject = new JSONObject();
        adopterObject.put("adopterId", adopterId);
        adopterObject.put("adopterChatId", adopterChatId);
        adopterObject.put("adopterLastname", adopterLastname);
        adopterObject.put("adopterFirstname", adopterFirstname);
        adopterObject.put("adopterMiddlename", adopterMiddlename);
        adopterObject.put("adopterPassport", adopterPassport);
        adopterObject.put("adopterTelNumber", adopterTelNumber);
        adopterObject.put("adopterAddress", adopterAddress);

        Adopter adopter = new Adopter();
        adopter.setAdopterId(adopterId);
        adopter.setAdopterChatId(adopterChatId);
        adopter.setAdopterLastname(adopterLastname);
        adopter.setAdopterFirstname(adopterFirstname);
        adopter.setAdopterMiddleName(adopterMiddlename);
        adopter.setAdopterPassport(adopterPassport);
        adopter.setAdopterTelNumber(adopterTelNumber);
        adopter.setAdopterAddress(adopterAddress);

        final List<Adopter> adopters = new ArrayList<>();
        adopters.add(adopter);

        when(adopterRepository.findAll()).thenReturn(adopters);
        when(adopterRepository.findByAdopterId(any(Long.class))).thenReturn(null);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/adopter/" + adopterId))
                .andExpect(status().isBadRequest());

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/adopter/")
                        .content(adopterObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void adopterFormatNotComplianceBadRequestTest() throws Exception {
        final long adopterId = 1L;
        final Integer adopterChatId = 123;
        final String adopterLastname = "Van";
        final String adopterFirstname = "Van";
        final String adopterMiddlename = "Van";
        final String adopterPassport = "Van";
        final String adopterTelNumber = "Van";
        final String adopterAddress = "Van";

        JSONObject adopterObject = new JSONObject();
        adopterObject.put("adopterId", adopterId);
        adopterObject.put("adopterChatId", adopterChatId);
        adopterObject.put("adopterLastname", adopterLastname);
        adopterObject.put("adopterFirstname", adopterFirstname);
        adopterObject.put("adopterMiddlename", adopterMiddlename);
        adopterObject.put("adopterPassport", adopterPassport);
        adopterObject.put("adopterTelNumber", adopterTelNumber);
        adopterObject.put("adopterAddress", adopterAddress);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/adopter/")
                        .content(adopterObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }
}
