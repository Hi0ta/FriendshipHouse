package sky.pro.friendshiphouse.repository;

import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sky.pro.friendshiphouse.model.AnimalDog;

import java.util.List;

@Repository
public interface AnimalDogRepository extends JpaRepository<AnimalDog, Long> {
    AnimalDog findByAnimalDogId(long animalDogId);

    @NotNull
    List<AnimalDog> findAll();

    List<AnimalDog> getAnimalDogByAnimalDogStatusFree(boolean StatusFree);
}
