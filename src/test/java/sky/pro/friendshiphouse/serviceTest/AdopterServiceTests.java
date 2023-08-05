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
import sky.pro.friendshiphouse.model.AnimalCat;
import sky.pro.friendshiphouse.model.AnimalDog;
import sky.pro.friendshiphouse.model.Report;
import sky.pro.friendshiphouse.repository.AdopterRepository;
import sky.pro.friendshiphouse.repository.ReportRepository;
import sky.pro.friendshiphouse.service.AdopterService;
import sky.pro.friendshiphouse.service.AnimalCatService;
import sky.pro.friendshiphouse.service.AnimalDogService;
import sky.pro.friendshiphouse.service.ReportService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AdopterServiceTests {

    @Mock
    private AdopterRepository adopterRepository;
    @Mock
    private ReportRepository reportRepository;

    @InjectMocks
    private AdopterService adopterService;
    @Mock
    private ReportService reportService;
    @Mock
    private AnimalCatService animalCatService;
    @Mock
    private AnimalDogService animalDogService;

    private Adopter adopter = new Adopter();
    private AnimalCat animalCat = new AnimalCat();
    private AnimalDog animalDog = new AnimalDog();
    private Report report = new Report();


    private final long adopterId = 1L;
    private final Long adopterChatId = 1234567890L;
    private final String adopterLastname = "Van";
    private final String adopterFirstname = "Van";
    private final String adopterMiddlename = "Van";
    private final String adopterPassport = "Van";
    private final String adopterTelNumber = "Van";
    private final String adopterAddress = "Van";
    private List<Adopter> adopters = new ArrayList<>();
    @Test
    public void checkGetAllAdopter(){
        adopters.add(adopter);
        when(adopterRepository.findAll()).thenReturn(adopters);
        Collection<Adopter> checkedAdopters = adopterService.getAllAdopter();
        assertEquals(checkedAdopters, adopters);
    }

    @Test
    public void checkGetAdoptersAvailabilityReport(){
        LocalDate date = LocalDate.now();
        adopters.add(adopter);
        when(adopterRepository.findAll()).thenReturn(adopters);
        when(reportService.getReportPerDay(date, adopter.getAdopterId())).thenReturn(null);

        List<Adopter> adopterWithReportIsNull = adopterService.getAdoptersAvailabilityReport(date);

        assertEquals(adopters, adopterWithReportIsNull);
    }

    @Test
    public void checkGetAdoptersTrialPeriodFinal(){
        adopters.add(adopter);
        Collection<Report> reports = new ArrayList<>();
        for (long i = 0L; i < 30; i++){
            report.setReportId(i);
            reports.add(report);
        }
        when(adopterRepository.findAll()).thenReturn(adopters);
        when(reportService.getReportsByAdopterId(adopter.getAdopterId())).thenReturn(reports);
        List<Adopter> adoptersTrialPeriodFinal = adopterService.getAdoptersTrialPeriodFinal();

        assertEquals(adopters, adoptersTrialPeriodFinal);
    }
    @Test
    public void checkGetAllAdopterByStatusBlackList(){
       adopters.add(adopter);
        boolean statusBlackList = true;
        when(adopterRepository.getAdopterByAdopterStatusBlackList(statusBlackList)).thenReturn(adopters);
        Collection<Adopter> checkAdopters = adopterService.getAllAdopterByStatusBlackList(statusBlackList);
        assertEquals(adopters, checkAdopters);
    }
    @Test
    public void checkGetAdopterById(){
        adopter.setAdopterId(adopterId);
        when(adopterRepository.findByAdopterId(adopterId)).thenReturn(adopter);
        Adopter checkedAdopter = adopterService.getAdopterById(adopterId);

        assertEquals(checkedAdopter, adopter);
    }

    @Test
    public void checkExceptionWhenGetAdopterById(){
        assertThrows(ObjectAbsenceException.class, () -> adopterService.getAdopterById(2L));
    }
    @Test
    public void checkGetAdopterByChatId(){
        adopter.setAdopterChatId(adopterChatId);
        when(adopterRepository.findByAdopterChatId(adopterChatId)).thenReturn(adopter);
        Adopter checkedAdopter = adopterService.getAdopterByChatId(adopterChatId);

        assertEquals(checkedAdopter, adopter);
    }

    @Test
    public void checkExceptionWhenGetAdopterByChatId(){
        assertThrows(ObjectAbsenceException.class, () -> adopterService.getAdopterByChatId(2L));
    }

    @Test
    public void checkCreateAdopterCat(){
        adopter.setAdopterId(adopterId);
        adopter.setAdopterChatId(adopterChatId);
        adopter.setAdopterLastname(adopterLastname);
        adopter.setAdopterFirstname(adopterFirstname);
        adopter.setAdopterMiddleName(adopterMiddlename);
        adopter.setAdopterPassport(adopterPassport);
        adopter.setAdopterTelNumber(adopterTelNumber);
        adopter.setAdopterAddress(adopterAddress);
        animalCat.setAnimalCatId(1L);
        adopter.setAnimalCat(animalCat);

        when(adopterRepository.save(adopter)).thenReturn(adopter);

        Adopter checkedAdopter = adopterService.createAdopter(adopter);
       // assertNotNull(checkedAdopter);
        assertEquals(checkedAdopter, adopter);
    }

    @Test
    public void checkCreateAdopterDog(){
        adopter.setAdopterId(adopterId);
        adopter.setAdopterChatId(adopterChatId);
        adopter.setAdopterLastname(adopterLastname);
        adopter.setAdopterFirstname(adopterFirstname);
        adopter.setAdopterMiddleName(adopterMiddlename);
        adopter.setAdopterPassport(adopterPassport);
        adopter.setAdopterTelNumber(adopterTelNumber);
        adopter.setAdopterAddress(adopterAddress);
        animalCat.setAnimalCatId(0L);
        adopter.setAnimalCat(animalCat);
        animalDog.setAnimalDogId(1L);
        adopter.setAnimalDog(animalDog);

        when(adopterRepository.save(adopter)).thenReturn(adopter);
        Adopter checkedAdopter = adopterService.createAdopter(adopter);

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
    public void checkEditAdopterStatusBlackList(){
        adopter.setAdopterId(adopterId);
        when(adopterRepository.findByAdopterId(adopter.getAdopterId())).thenReturn(adopter);
        when(adopterRepository.save(adopter)).thenReturn(adopter);
        adopterService.editAdopterStatusBlackList(adopterId);
        verify(adopterRepository, times(1)).findByAdopterId(adopterId);
        verify(adopterRepository, times(1)).save(adopter);
    }

    @Test
    public void checkExceptionWhenEditAdopterStatusBlackList(){
        when(adopterRepository.findByAdopterId(adopterId)).thenReturn(null);
        assertThrows(ObjectAbsenceException.class, () -> adopterService.editAdopterStatusBlackList(adopterId));
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
