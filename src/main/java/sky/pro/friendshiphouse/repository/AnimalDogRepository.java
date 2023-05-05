package sky.pro.friendshiphouse.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sky.pro.friendshiphouse.model.AnimalDog;

import java.util.List;


public interface AnimalDogRepository extends JpaRepository<AnimalDog, Long> {
    AnimalDog findByAnimalDogId(long animalDogId);
    List<AnimalDog> findAll();
}
