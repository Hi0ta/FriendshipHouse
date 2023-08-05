package sky.pro.friendshiphouse.serviceTest;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import sky.pro.friendshiphouse.constant.AnimalCatKind;
import sky.pro.friendshiphouse.exception.ObjectAbsenceException;
import sky.pro.friendshiphouse.exception.ObjectAlreadyExistsException;
import sky.pro.friendshiphouse.model.AnimalCat;
import sky.pro.friendshiphouse.repository.AnimalCatRepository;
import sky.pro.friendshiphouse.service.AnimalCatService;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;
import static sky.pro.friendshiphouse.constant.AnimalCatKind.KITTY;

@ExtendWith(MockitoExtension.class)
public class AnimalCatServiceTests {
    @Mock
    private AnimalCatRepository animalCatRepository;

    @InjectMocks
    private AnimalCatService animalCatService;

    private AnimalCat animalCat = new AnimalCat();

    final long animalCatId = 1L;
    final String animalCatName = "Van";
    final int animalCatAge = 2;
    final String animalCatBreed = "Van";
    final String animalCatInfo = "Van";
    final AnimalCatKind animalCatKind = KITTY;
    final boolean animalCatStatusFree = true;
    List<AnimalCat> animalCats = new ArrayList<>();

    @Test
    public void checkGetAllAnimalCat(){
        animalCats.add(animalCat);
        when(animalCatRepository.findAll()).thenReturn(animalCats);
        Collection<AnimalCat> checkedAnimalCats = animalCatService.getAllAnimalCat();
        assertEquals(checkedAnimalCats, animalCats);
    }

    @Test
    public void checkGetAnimalCatByAnimalCatStatusFree() {
        animalCat.setAnimalCatStatusFree(animalCatStatusFree);
        animalCats.add(animalCat);
        when(animalCatRepository.getAnimalCatByAnimalCatStatusFree(animalCatStatusFree)).thenReturn(animalCats);
        Collection<AnimalCat> checkedAnimalCats = animalCatService.getAnimalCatByAnimalCatStatusFree(animalCatStatusFree);
        assertEquals(checkedAnimalCats,animalCats);
    }

    @Test
    public void checkGetAnimalCatById(){
        animalCat.setAnimalCatId(animalCatId);
        when(animalCatRepository.findByAnimalCatId(animalCatId)).thenReturn(animalCat);
        AnimalCat checkedAnimalCat = animalCatService.getAnimalCatById(animalCatId);
        assertEquals(checkedAnimalCat, animalCat);
    }

    @Test
    public void checkExceptionWhenGetAnimalCatById(){
        assertThrows(ObjectAbsenceException.class, () -> animalCatService.getAnimalCatById(2L));
    }

    @Test
    public void checkCreateAnimalDog(){
        animalCat.setAnimalCatId(animalCatId);
        animalCat.setAnimalCatName(animalCatName);
        animalCat.setAnimalCatAge(animalCatAge);
        animalCat.setAnimalCatBreed(animalCatBreed);
        animalCat.setAnimalCatInfo(animalCatInfo);
        animalCat.setAnimalCatKind(animalCatKind);
        animalCat.setAnimalCatStatusFree(animalCatStatusFree);

        when(animalCatRepository.save(animalCat)).thenReturn(animalCat);
        AnimalCat checkedAnimalCat = animalCatService.createAnimalCat(animalCat);
        assertEquals(checkedAnimalCat, animalCat);
    }

    @Test
    public void checkExceptionWhenCreateAnimalDog(){
        animalCat.setAnimalCatId(animalCatId);
        animalCat.setAnimalCatName(animalCatName);
        animalCat.setAnimalCatAge(animalCatAge);
        animalCat.setAnimalCatBreed(animalCatBreed);
        animalCat.setAnimalCatInfo(animalCatInfo);
        animalCat.setAnimalCatKind(animalCatKind);
        animalCat.setAnimalCatStatusFree(animalCatStatusFree);
        animalCats.add(animalCat);

        when(animalCatRepository.findAll()).thenReturn(animalCats);
        assertThrows(ObjectAlreadyExistsException.class, () -> animalCatService.createAnimalCat(animalCat));
    }

    @Test
    public void checkEditAnimalDog() {
        animalCat.setAnimalCatId(animalCatId);
        animalCat.setAnimalCatName(animalCatName);
        animalCat.setAnimalCatAge(animalCatAge);
        animalCat.setAnimalCatBreed(animalCatBreed);
        animalCat.setAnimalCatInfo(animalCatInfo);
        animalCat.setAnimalCatKind(animalCatKind);
        animalCat.setAnimalCatStatusFree(animalCatStatusFree);

        when(animalCatRepository.findByAnimalCatId(animalCatId)).thenReturn(animalCat);
        when(animalCatRepository.save(animalCat)).thenReturn(animalCat);
        AnimalCat checkedAnimalCat = animalCatService.editAnimalCat(animalCat);
        assertEquals(checkedAnimalCat, animalCat);
    }

    @Test
    public void checkExceptionWhenEditAnimalCat(){
        when(animalCatRepository.findByAnimalCatId(animalCat.getAnimalCatId())).thenReturn(null);
        assertThrows(ObjectAbsenceException.class, () -> animalCatService.editAnimalCat(animalCat));
    }

    @Test
    public void checkEditAnimalCatStatus(){
        animalCat.setAnimalCatId(animalCatId);
        animalCat.setAnimalCatStatusFree(animalCatStatusFree);
        when(animalCatRepository.findByAnimalCatId(animalCatId)).thenReturn(animalCat);
        when(animalCatRepository.save(animalCat)).thenReturn(animalCat);
        AnimalCat checkedAnimalCat = animalCatService.editAnimalCatStatus(animalCat.getAnimalCatId(), false);
        assertEquals(checkedAnimalCat, animalCat);
    }

    @Test
    public void checkExceptionWhenEditAnimalCatStatus(){
        when(animalCatRepository.findByAnimalCatId(animalCat.getAnimalCatId())).thenReturn(null);
        assertThrows(ObjectAbsenceException.class, () -> animalCatService.editAnimalCatStatus(animalCat.getAnimalCatId(), false));
    }

    @Test
    public void checkDeleteAnimalCat(){
        animalCat.setAnimalCatId(animalCatId);
        when(animalCatRepository.findByAnimalCatId(animalCatId)).thenReturn(animalCat);
        animalCatService.deleteAnimalCat(animalCatId);
        verify(animalCatRepository, times(1)).deleteById(animalCatId);
    }



    @Test
    public void checkExceptionWhenDeleteAnimalCat(){
        when(animalCatRepository.findByAnimalCatId(animalCatId)).thenReturn(null);
        assertThrows(ObjectAbsenceException.class, () -> animalCatService.deleteAnimalCat(animalCatId));
    }
}
