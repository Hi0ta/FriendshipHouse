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
import sky.pro.friendshiphouse.controller.VolunteerController;
import sky.pro.friendshiphouse.model.Volunteer;
import sky.pro.friendshiphouse.repository.AdopterRepository;
import sky.pro.friendshiphouse.repository.AnimalDogRepository;
import sky.pro.friendshiphouse.repository.VolunteerRepository;
import sky.pro.friendshiphouse.service.AdopterService;
import sky.pro.friendshiphouse.service.AnimalDogService;
import sky.pro.friendshiphouse.service.VolunteerService;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
public class VolunteerControllerTests {

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

    @InjectMocks
    private VolunteerController volunteerController;

    @Test
    public void volunteerTest() throws Exception {
        final Long volunteerId = 1L;
        final Integer volunteerChatId = 1234567890;
        final String volunteerName = "Van";
        final boolean volunteerStatusFree = true;

        JSONObject volunteerObject = new JSONObject();
        volunteerObject.put("volunteerId", volunteerId);
        volunteerObject.put("volunteerChatId", volunteerChatId);
        volunteerObject.put("volunteerName", volunteerName);
        volunteerObject.put("volunteerStatusFree", volunteerStatusFree);

        Volunteer volunteer = new Volunteer();
        volunteer.setVolunteerId(volunteerId);
        volunteer.setVolunteerChatId(volunteerChatId);
        volunteer.setVolunteerName(volunteerName);
        volunteer.setVolunteerStatusFree(volunteerStatusFree);

        when(volunteerRepository.save(any(Volunteer.class))).thenReturn(volunteer);
        when(volunteerRepository.findByVolunteerId(any(Long.class))).thenReturn(volunteer);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/volunteer/")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/volunteer/" + volunteerId)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.volunteerId").value(volunteerId))
                .andExpect(jsonPath("$.volunteerChatId").value(volunteerChatId))
                .andExpect(jsonPath("$.volunteerName").value(volunteerName))
                .andExpect(jsonPath("$.volunteerStatusFree").value(volunteerStatusFree));

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/volunteer/")
                        .content(volunteerObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.volunteerId").value(volunteerId))
                .andExpect(jsonPath("$.volunteerChatId").value(volunteerChatId))
                .andExpect(jsonPath("$.volunteerName").value(volunteerName))
                .andExpect(jsonPath("$.volunteerStatusFree").value(volunteerStatusFree));

        mockMvc.perform(MockMvcRequestBuilders
                        .put("/volunteer/")
                        .content(volunteerObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.volunteerId").value(volunteerId))
                .andExpect(jsonPath("$.volunteerChatId").value(volunteerChatId))
                .andExpect(jsonPath("$.volunteerName").value(volunteerName))
                .andExpect(jsonPath("$.volunteerStatusFree").value(volunteerStatusFree));

        mockMvc.perform(MockMvcRequestBuilders
                        .put("/volunteer/change-status/" + volunteerId + "?volunteerStatusFree=" + volunteerStatusFree)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/volunteer/" + volunteerId)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void volunteerBadRequestTest() throws Exception {
        final Long volunteerId = 1L;
        final Integer volunteerChatId = 1234567890;
        final String volunteerName = "Van";
        final boolean volunteerStatusFree = true;

        JSONObject volunteerObject = new JSONObject();
        volunteerObject.put("volunteerId", volunteerId);
        volunteerObject.put("volunteerChatId", volunteerChatId);
        volunteerObject.put("volunteerName", volunteerName);
        volunteerObject.put("volunteerStatusFree", volunteerStatusFree);

        Volunteer volunteer = new Volunteer();
        volunteer.setVolunteerId(volunteerId);
        volunteer.setVolunteerChatId(volunteerChatId);
        volunteer.setVolunteerName(volunteerName);
        volunteer.setVolunteerStatusFree(volunteerStatusFree);

        final List<Volunteer> volunteers = new ArrayList<>();
        volunteers.add(volunteer);

        when(volunteerRepository.findAll()).thenReturn(volunteers);
        when(volunteerRepository.findByVolunteerId(any(Long.class))).thenReturn(null);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/volunteer/" + volunteerId))
                .andExpect(status().isBadRequest());

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/volunteer/")
                        .content(volunteerObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void volunteerFormatNotComplianceBadRequestTest() throws Exception {
        final Long volunteerId = 1L;
        final Integer volunteerChatId = 123;
        final String volunteerName = "Van";
        final boolean volunteerStatusFree = true;

        JSONObject volunteerObject = new JSONObject();
        volunteerObject.put("volunteerId", volunteerId);
        volunteerObject.put("volunteerChatId", volunteerChatId);
        volunteerObject.put("volunteerName", volunteerName);
        volunteerObject.put("volunteerStatusFree", volunteerStatusFree);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/volunteer/")
                        .content(volunteerObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }
}
