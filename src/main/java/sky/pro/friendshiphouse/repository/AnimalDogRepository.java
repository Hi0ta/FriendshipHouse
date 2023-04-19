package sky.pro.friendshiphouse.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sky.pro.friendshiphouse.model.AnimalDog;


public interface AnimalDogRepository extends JpaRepository<AnimalDog, Long> {
    AnimalDog findById(long id);
}
