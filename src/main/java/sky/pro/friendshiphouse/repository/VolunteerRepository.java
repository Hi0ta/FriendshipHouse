package sky.pro.friendshiphouse.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sky.pro.friendshiphouse.model.Volunteer;

import java.util.List;


public interface VolunteerRepository extends JpaRepository<Volunteer, Long> {
    Volunteer findByVolunteerId(long volunteerId);
    List<Volunteer> findAll();
}
