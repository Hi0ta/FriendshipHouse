package sky.pro.friendshiphouse.serviceTest;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import sky.pro.friendshiphouse.exception.FormatNotComplianceException;
import sky.pro.friendshiphouse.exception.ObjectAbsenceException;
import sky.pro.friendshiphouse.exception.ObjectAlreadyExistsException;
import sky.pro.friendshiphouse.model.Adopter;
import sky.pro.friendshiphouse.repository.AdopterRepository;
import sky.pro.friendshiphouse.service.AdopterService;

import java.util.ArrayList;
import java.util.List;
import java.util.Collection;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AdopterServiceTests {

    @Mock
    private AdopterRepository adopterRepository;

    @InjectMocks
    private AdopterService adopterService;

    private Adopter adopter = new Adopter();

    final long adopterId = 1L;
    final Long adopterChatId = 1234567890L;
    final String adopterLastname = "Van";
    final String adopterFirstname = "Van";
    final String adopterMiddlename = "Van";
    final String adopterPassport = "Van";
    final String adopterTelNumber = "Van";
    final String adopterAddress = "Van";

    @Test
    public void checkGetAllAdopter(){
        adopter.setAdopterId(adopterId);
        adopter.setAdopterChatId(adopterChatId);
        adopter.setAdopterLastname(adopterLastname);
        adopter.setAdopterFirstname(adopterFirstname);
        adopter.setAdopterMiddleName(adopterMiddlename);
        adopter.setAdopterPassport(adopterPassport);
        adopter.setAdopterTelNumber(adopterTelNumber);
        adopter.setAdopterAddress(adopterAddress);


        List<Adopter> standardAdopters = new ArrayList<>();
        standardAdopters.add(adopter);

        when(adopterRepository.findAll()).thenReturn(standardAdopters);

        Collection<Adopter> checkedAdopters = adopterService.getAllAdopter();

        assertEquals(checkedAdopters, standardAdopters);
    }

    @Test
    public void checkGetAdopterById(){
        adopter.setAdopterId(adopterId);
        adopter.setAdopterChatId(adopterChatId);
        adopter.setAdopterLastname(adopterLastname);
        adopter.setAdopterFirstname(adopterFirstname);
        adopter.setAdopterMiddleName(adopterMiddlename);
        adopter.setAdopterPassport(adopterPassport);
        adopter.setAdopterTelNumber(adopterTelNumber);
        adopter.setAdopterAddress(adopterAddress);

        when(adopterRepository.findByAdopterId(adopterId)).thenReturn(adopter);
        Adopter checkedAdopter = adopterService.getAdopterById(adopterId);

        assertEquals(checkedAdopter, adopter);
    }

    @Test
    public void checkExceptionWhenGetAdopterById(){
        assertThrows(ObjectAbsenceException.class, () -> adopterService.getAdopterById(2L));
    }

    @Test
    public void checkCreateAdopter(){
        adopter.setAdopterId(adopterId);
        adopter.setAdopterChatId(adopterChatId);
        adopter.setAdopterLastname(adopterLastname);
        adopter.setAdopterFirstname(adopterFirstname);
        adopter.setAdopterMiddleName(adopterMiddlename);
        adopter.setAdopterPassport(adopterPassport);
        adopter.setAdopterTelNumber(adopterTelNumber);
        adopter.setAdopterAddress(adopterAddress);

        when(adopterRepository.save(adopter)).thenReturn(adopter);
        Adopter checkedAdopter = adopterService.createAdopter(adopter);

       // assertNotNull(checkedAdopter);
        assertEquals(checkedAdopter, adopter);

    }

    @Test
    public void checkExceptionWhenCreateAdopter(){
        adopter.setAdopterChatId(123L);
        assertThrows(FormatNotComplianceException.class, () -> adopterService.createAdopter(adopter));
    }

    @Test
    public void checkExceptionWhenCreateAdopter2(){
        adopter.setAdopterId(adopterId);
        adopter.setAdopterChatId(adopterChatId);
        adopter.setAdopterLastname(adopterLastname);
        adopter.setAdopterFirstname(adopterFirstname);
        adopter.setAdopterMiddleName(adopterMiddlename);
        adopter.setAdopterPassport(adopterPassport);
        adopter.setAdopterTelNumber(adopterTelNumber);
        adopter.setAdopterAddress(adopterAddress);

        List<Adopter> adopters = new ArrayList<>();
        adopters.add(adopter);

        when(adopterRepository.findAll()).thenReturn(adopters);

        assertThrows(ObjectAlreadyExistsException.class, () -> adopterService.createAdopter(adopter));
    }

    @Test
    public void checkEditAdopter() {
        adopter.setAdopterId(adopterId);
        adopter.setAdopterChatId(adopterChatId);
        adopter.setAdopterLastname(adopterLastname);
        adopter.setAdopterFirstname(adopterFirstname);
        adopter.setAdopterMiddleName(adopterMiddlename);
        adopter.setAdopterPassport(adopterPassport);
        adopter.setAdopterTelNumber(adopterTelNumber);
        adopter.setAdopterAddress(adopterAddress);

        when(adopterRepository.findByAdopterId(adopterId)).thenReturn(adopter);
        when(adopterRepository.save(adopter)).thenReturn(adopter);
        Adopter checkedAdopter = adopterService.editAdopter(adopter);

        assertEquals(checkedAdopter, adopter);
    }

    @Test
    public void checkExceptionWhenEditAdopter(){
        when(adopterRepository.findByAdopterId(adopter.getAdopterId())).thenReturn(null);
        assertThrows(ObjectAbsenceException.class, () -> adopterService.editAdopter(adopter));
    }

    @Test
    public void checkExceptionWhenEditAdopter2(){
        adopter.setAdopterId(adopterId);
        adopter.setAdopterChatId(123L);
        when(adopterRepository.findByAdopterId(adopterId)).thenReturn(adopter);
        assertThrows(FormatNotComplianceException.class, () -> adopterService.editAdopter(adopter));
    }

    @Test
    public void checkDeleteAdopter(){
        adopter.setAdopterId(adopterId);
        when(adopterRepository.findByAdopterId(adopterId)).thenReturn(adopter);
        adopterService.deleteAdopter(adopterId);
        verify(adopterRepository, times(1)).deleteById(adopterId);
    }

    @Test
    public void checkExceptionWhenDeleteAdopter(){
        when(adopterRepository.findByAdopterId(adopterId)).thenReturn(null);
        assertThrows(ObjectAbsenceException.class, () -> adopterService.deleteAdopter(adopterId));
    }
}
