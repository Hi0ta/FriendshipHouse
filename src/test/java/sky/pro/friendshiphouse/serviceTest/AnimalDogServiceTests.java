package sky.pro.friendshiphouse.serviceTest;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import sky.pro.friendshiphouse.constant.AnimalDogKind;
import sky.pro.friendshiphouse.exception.ObjectAbsenceException;
import sky.pro.friendshiphouse.exception.ObjectAlreadyExistsException;
import sky.pro.friendshiphouse.model.AnimalDog;
import sky.pro.friendshiphouse.repository.AnimalDogRepository;
import sky.pro.friendshiphouse.service.AnimalDogService;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;
import static sky.pro.friendshiphouse.constant.AnimalDogKind.PUPPY;

@ExtendWith(MockitoExtension.class)
public class AnimalDogServiceTests {

    @Mock
    private AnimalDogRepository animalDogRepository;

    @InjectMocks
    private AnimalDogService animalDogService;

    private AnimalDog animalDog = new AnimalDog();

    final long animalDogId = 1L;
    final String animalDogName = "Van";
    final int animalDogAge = 2;
    final String animalDogBreed = "Van";
    final String animalDogInfo = "Van";
    final AnimalDogKind animalDogKind = PUPPY;
    final boolean animalDogStatusFree = true;

    @Test
    public void checkGetAllAnimalDog(){
        animalDog.setAnimalDogId(animalDogId);
        animalDog.setAnimalDogName(animalDogName);
        animalDog.setAnimalDogAge(animalDogAge);
        animalDog.setAnimalDogBreed(animalDogBreed);
        animalDog.setAnimalDogInfo(animalDogInfo);
        animalDog.setAnimalDogKind(animalDogKind);
        animalDog.setAnimalDogStatusFree(animalDogStatusFree);

        List<AnimalDog> standardAnimalDogs = new ArrayList<>();
        standardAnimalDogs.add(animalDog);

        when(animalDogRepository.findAll()).thenReturn(standardAnimalDogs);

        Collection<AnimalDog> checkedAnimalDogs = animalDogService.getAllAnimalDog();

        assertEquals(checkedAnimalDogs, standardAnimalDogs);
    }

    @Test
    public void checkGetAnimalDogById(){
        animalDog.setAnimalDogId(animalDogId);
        animalDog.setAnimalDogName(animalDogName);
        animalDog.setAnimalDogAge(animalDogAge);
        animalDog.setAnimalDogBreed(animalDogBreed);
        animalDog.setAnimalDogInfo(animalDogInfo);
        animalDog.setAnimalDogKind(animalDogKind);
        animalDog.setAnimalDogStatusFree(animalDogStatusFree);

        when(animalDogRepository.findByAnimalDogId(animalDogId)).thenReturn(animalDog);
        AnimalDog checkedAnimalDog = animalDogService.getAnimalDogById(animalDogId);

        assertEquals(checkedAnimalDog, animalDog);
    }

    @Test
    public void checkExceptionWhenGetAnimalDogById(){
        assertThrows(ObjectAbsenceException.class, () -> animalDogService.getAnimalDogById(2L));
    }

    @Test
    public void checkCreateAnimalDog(){
        animalDog.setAnimalDogId(animalDogId);
        animalDog.setAnimalDogName(animalDogName);
        animalDog.setAnimalDogAge(animalDogAge);
        animalDog.setAnimalDogBreed(animalDogBreed);
        animalDog.setAnimalDogInfo(animalDogInfo);
        animalDog.setAnimalDogKind(animalDogKind);
        animalDog.setAnimalDogStatusFree(animalDogStatusFree);

        when(animalDogRepository.save(animalDog)).thenReturn(animalDog);
        AnimalDog checkedAnimalDog = animalDogService.createAnimalDog(animalDog);

        assertEquals(checkedAnimalDog, animalDog);
    }

    @Test
    public void checkExceptionWhenCreateAnimalDog(){
        animalDog.setAnimalDogId(animalDogId);
        animalDog.setAnimalDogName(animalDogName);
        animalDog.setAnimalDogAge(animalDogAge);
        animalDog.setAnimalDogBreed(animalDogBreed);
        animalDog.setAnimalDogInfo(animalDogInfo);
        animalDog.setAnimalDogKind(animalDogKind);
        animalDog.setAnimalDogStatusFree(animalDogStatusFree);

        List<AnimalDog> animalDogs = new ArrayList<>();
        animalDogs.add(animalDog);

        when(animalDogRepository.findAll()).thenReturn(animalDogs);
        assertThrows(ObjectAlreadyExistsException.class, () -> animalDogService.createAnimalDog(animalDog));
    }

    @Test
    public void checkEditAnimalDog() {
        animalDog.setAnimalDogId(animalDogId);
        animalDog.setAnimalDogName(animalDogName);
        animalDog.setAnimalDogAge(animalDogAge);
        animalDog.setAnimalDogBreed(animalDogBreed);
        animalDog.setAnimalDogInfo(animalDogInfo);
        animalDog.setAnimalDogKind(animalDogKind);
        animalDog.setAnimalDogStatusFree(animalDogStatusFree);

        when(animalDogRepository.findByAnimalDogId(animalDogId)).thenReturn(animalDog);
        when(animalDogRepository.save(animalDog)).thenReturn(animalDog);
        AnimalDog checkedAnimalDog = animalDogService.editAnimalDog(animalDog);

        assertEquals(checkedAnimalDog, animalDog);
    }

    @Test
    public void checkExceptionWhenEditAnimalDog(){
        when(animalDogRepository.findByAnimalDogId(animalDog.getAnimalDogId())).thenReturn(null);
        assertThrows(ObjectAbsenceException.class, () -> animalDogService.editAnimalDog(animalDog));
    }

    @Test
    public void checkEditAnimalDogStatus(){
        animalDog.setAnimalDogId(animalDogId);
        animalDog.setAnimalDogName(animalDogName);
        animalDog.setAnimalDogAge(animalDogAge);
        animalDog.setAnimalDogBreed(animalDogBreed);
        animalDog.setAnimalDogInfo(animalDogInfo);
        animalDog.setAnimalDogKind(animalDogKind);
        animalDog.setAnimalDogStatusFree(animalDogStatusFree);

        when(animalDogRepository.findByAnimalDogId(animalDogId)).thenReturn(animalDog);
        when(animalDogRepository.save(animalDog)).thenReturn(animalDog);
        AnimalDog checkedAnimalDog = animalDogService.editAnimalDogStatus(animalDog.getAnimalDogId(), false);

        assertEquals(checkedAnimalDog, animalDog);
    }

    @Test
    public void checkExceptionWhenEditAnimalDogStatus(){
        when(animalDogRepository.findByAnimalDogId(animalDog.getAnimalDogId())).thenReturn(null);
        assertThrows(ObjectAbsenceException.class, () -> animalDogService.editAnimalDogStatus(animalDog.getAnimalDogId(), false));
    }

    @Test
    public void checkDeleteAnimalDog(){
        animalDog.setAnimalDogId(animalDogId);
        when(animalDogRepository.findByAnimalDogId(animalDogId)).thenReturn(animalDog);
        animalDogService.deleteAnimalDog(animalDogId);
        verify(animalDogRepository, times(1)).deleteById(animalDogId);
    }



    @Test
    public void checkExceptionWhenDeleteAnimalDog(){
        when(animalDogRepository.findByAnimalDogId(animalDogId)).thenReturn(null);
        assertThrows(ObjectAbsenceException.class, () -> animalDogService.deleteAnimalDog(animalDogId));
    }
}
