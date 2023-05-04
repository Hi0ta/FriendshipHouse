package sky.pro.friendshiphouse.serviceTest;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import sky.pro.friendshiphouse.exception.FormatNotComplianceException;
import sky.pro.friendshiphouse.exception.ObjectAbsenceException;
import sky.pro.friendshiphouse.exception.ObjectAlreadyExistsException;
import sky.pro.friendshiphouse.model.Volunteer;
import sky.pro.friendshiphouse.repository.VolunteerRepository;
import sky.pro.friendshiphouse.service.VolunteerService;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class VolunteerServiceTests {

    @Mock
    private VolunteerRepository volunteerRepository;

    @InjectMocks
    private VolunteerService volunteerService;

    private Volunteer volunteer = new Volunteer();

    final Long volunteerId = 1L;
    final Integer volunteerChatId = 1234567890;
    final String volunteerName = "Van";
    final boolean volunteerStatusFree = true;

    @Test
    public void checkGetAllVolunteer() {
        volunteer.setVolunteerId(volunteerId);
        volunteer.setVolunteerChatId(volunteerChatId);
        volunteer.setVolunteerName(volunteerName);
        volunteer.setVolunteerStatusFree(volunteerStatusFree);

        List<Volunteer> standardVolunteers = new ArrayList<>();
        standardVolunteers.add(volunteer);

        when(volunteerRepository.findAll()).thenReturn(standardVolunteers);

        Collection<Volunteer> checkedVolunteers = volunteerService.getAllVolunteer();

        assertEquals(checkedVolunteers, standardVolunteers);
    }

    @Test
    public void checkGetVolunteerById() {
        volunteer.setVolunteerId(volunteerId);
        volunteer.setVolunteerChatId(volunteerChatId);
        volunteer.setVolunteerName(volunteerName);
        volunteer.setVolunteerStatusFree(volunteerStatusFree);

        when(volunteerRepository.findByVolunteerId(volunteerId)).thenReturn(volunteer);
        Volunteer checkedVolunteer = volunteerService.getVolunteerById(volunteerId);

        assertEquals(checkedVolunteer, volunteer);
    }

    @Test
    public void checkExceptionWhenGetVolunteerById() {
        assertThrows(ObjectAbsenceException.class, () -> volunteerService.getVolunteerById(2L));
    }

    @Test
    public void checkCreateVolunteer() {
        volunteer.setVolunteerId(volunteerId);
        volunteer.setVolunteerChatId(volunteerChatId);
        volunteer.setVolunteerName(volunteerName);
        volunteer.setVolunteerStatusFree(volunteerStatusFree);

        when(volunteerRepository.save(volunteer)).thenReturn(volunteer);
        Volunteer checkedVolunteer = volunteerService.createVolunteer(volunteer);

        assertEquals(checkedVolunteer, volunteer);
    }

    @Test
    public void checkExceptionWhenCreateVolunteer() {
        volunteer.setVolunteerChatId(123);
        assertThrows(FormatNotComplianceException.class, () -> volunteerService.createVolunteer(volunteer));
    }

    @Test
    public void checkExceptionWhenCreateVolunteer2() {
        volunteer.setVolunteerId(volunteerId);
        volunteer.setVolunteerChatId(volunteerChatId);
        volunteer.setVolunteerName(volunteerName);
        volunteer.setVolunteerStatusFree(volunteerStatusFree);

        List<Volunteer> volunteers = new ArrayList<>();
        volunteers.add(volunteer);

        when(volunteerRepository.findAll()).thenReturn(volunteers);
        assertThrows(ObjectAlreadyExistsException.class, () -> volunteerService.createVolunteer(volunteer));
    }

    @Test
    public void checkEditVolunteer() {
        volunteer.setVolunteerId(volunteerId);
        volunteer.setVolunteerChatId(volunteerChatId);
        volunteer.setVolunteerName(volunteerName);
        volunteer.setVolunteerStatusFree(volunteerStatusFree);

        when(volunteerRepository.findByVolunteerId(volunteerId)).thenReturn(volunteer);
        when(volunteerRepository.save(volunteer)).thenReturn(volunteer);
        Volunteer checkedVolunteer = volunteerService.editVolunteer(volunteer);

        assertEquals(checkedVolunteer, volunteer);
    }

    @Test
    public void checkExceptionWhenEditVolunteer() {
        when(volunteerRepository.findByVolunteerId(volunteer.getVolunteerId())).thenReturn(null);
        assertThrows(ObjectAbsenceException.class, () -> volunteerService.editVolunteer(volunteer));
    }

    @Test
    public void checkExceptionWhenEditVolunteer2(){
        volunteer.setVolunteerId(volunteerId);
        volunteer.setVolunteerChatId(123);
        when(volunteerRepository.findByVolunteerId(volunteerId)).thenReturn(volunteer);
        assertThrows(FormatNotComplianceException.class, () -> volunteerService.editVolunteer(volunteer));
    }

    @Test
    public void checkEditVolunteerStatus() {
        volunteer.setVolunteerId(volunteerId);
        volunteer.setVolunteerChatId(volunteerChatId);
        volunteer.setVolunteerName(volunteerName);
        volunteer.setVolunteerStatusFree(volunteerStatusFree);

        when(volunteerRepository.findByVolunteerId(volunteerId)).thenReturn(volunteer);
        when(volunteerRepository.save(volunteer)).thenReturn(volunteer);
        Volunteer checkedVolunteer = volunteerService.editVolunteerStatus(volunteer.getVolunteerId(), false);

        assertEquals(checkedVolunteer, volunteer);
    }

    @Test
    public void checkExceptionWhenEditVolunteerStatus() {
        when(volunteerRepository.findByVolunteerId(volunteer.getVolunteerId())).thenReturn(null);
        assertThrows(ObjectAbsenceException.class, () -> volunteerService.editVolunteerStatus(volunteer.getVolunteerId(), false));
    }

    @Test
    public void checkDeleteVolunteer() {
// как проверять void ???
    }


    @Test
    public void checkExceptionWhenDeleteVolunteer() {
        when(volunteerRepository.findByVolunteerId(volunteerId)).thenReturn(null);
        assertThrows(ObjectAbsenceException.class, () -> volunteerService.deleteVolunteer(volunteerId));
    }
}
