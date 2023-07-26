package sky.pro.friendshiphouse.repository;

import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sky.pro.friendshiphouse.model.AnimalCat;
import java.util.List;

@Repository
public interface AnimalCatRepository extends JpaRepository<AnimalCat, Long> {
    AnimalCat findByAnimalCatId(long animalCatId);


    @NotNull
    List<AnimalCat>findAll();
    List<AnimalCat>getAnimalCatByAnimalCatStatusFree(boolean statusFree);
}
