package sky.pro.friendshiphouse.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sky.pro.friendshiphouse.model.Adopter;

import java.util.List;


public interface AdopterRepository extends JpaRepository<Adopter, Long> {
    Adopter findByAdopterId(long adopterId);
    List<Adopter> findAll();

}
