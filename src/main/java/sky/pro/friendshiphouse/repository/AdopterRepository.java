package sky.pro.friendshiphouse.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sky.pro.friendshiphouse.model.Adopter;

public interface AdopterRepository extends JpaRepository<Adopter, Long> {
}
