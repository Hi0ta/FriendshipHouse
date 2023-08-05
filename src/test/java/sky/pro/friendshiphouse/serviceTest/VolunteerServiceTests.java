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
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class VolunteerServiceTests {

    @Mock
    private VolunteerRepository volunteerRepository;

    @InjectMocks
    private VolunteerService volunteerService;

    private Volunteer volunteer = new Volunteer();
    private final Long volunteerId = 1L;
    private final Long volunteerChatId = 1234567890L;
    private final String volunteerName = "Van";
    private final boolean volunteerStatusFree = true;
    private   List<Volunteer> volunteers = new ArrayList<>();
    @Test
    public void checkGetAllVolunteer() {
        volunteers.add(volunteer);
        when(volunteerRepository.findAll()).thenReturn(volunteers);
        Collection<Volunteer> checkedVolunteers = volunteerService.getAllVolunteer();
        assertEquals(checkedVolunteers, volunteers);
    }

    @Test
    public void checkGetVolunteerById() {
        volunteer.setVolunteerId(volunteerId);
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
        volunteer.setVolunteerChatId(123L);
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
        volunteer.setVolunteerChatId(123L);
        when(volunteerRepository.findByVolunteerId(volunteerId)).thenReturn(volunteer);
        assertThrows(FormatNotComplianceException.class, () -> volunteerService.editVolunteer(volunteer));
    }

    @Test
    public void checkEditVolunteerStatus() {
        volunteer.setVolunteerId(volunteerId);
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
        volunteer.setVolunteerId(volunteerId);
        when(volunteerRepository.findByVolunteerId(volunteerId)).thenReturn(volunteer);
        volunteerService.deleteVolunteer(volunteerId);
        verify(volunteerRepository, times(1)).deleteById(volunteerId);
    }


    @Test
    public void checkExceptionWhenDeleteVolunteer() {
        when(volunteerRepository.findByVolunteerId(volunteerId)).thenReturn(null);
        assertThrows(ObjectAbsenceException.class, () -> volunteerService.deleteVolunteer(volunteerId));
    }
    @Test
    public void checkCallVolunteerStatusFreeIsTrue() {
        volunteers.add(volunteer);
        when(volunteerRepository.getVolunteerByVolunteerStatusFreeIsTrue()).thenReturn(volunteers);
        Volunteer checkedVolunteer = volunteerService.callVolunteer();
        assertEquals(checkedVolunteer, volunteer);
    }

    @Test
    public void checkCallVolunteerStatusFreeIsFalse() {
        List<Volunteer> trueVolunteers = new ArrayList<>();
        volunteers.add(volunteer);
        when(volunteerRepository.getVolunteerByVolunteerStatusFreeIsTrue()).thenReturn(trueVolunteers);
        when(volunteerRepository.getVolunteerByVolunteerStatusFreeIsFalse()).thenReturn(volunteers);
        Volunteer checkedVolunteer = volunteerService.callVolunteer();
        assertEquals(checkedVolunteer, volunteer);
    }
}
