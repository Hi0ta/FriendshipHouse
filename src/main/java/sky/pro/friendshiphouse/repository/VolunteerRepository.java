package sky.pro.friendshiphouse.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sky.pro.friendshiphouse.model.Volunteer;


public interface VolunteerRepository extends JpaRepository<Volunteer, Long> {
    Volunteer findById(long id);
}
