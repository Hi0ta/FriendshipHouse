package sky.pro.friendshiphouse.repository;

import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sky.pro.friendshiphouse.model.Volunteer;

import java.util.List;

@Repository
public interface VolunteerRepository extends JpaRepository<Volunteer, Long> {
    Volunteer findByVolunteerId(long volunteerId);

    @NotNull
    List<Volunteer> findAll();

    List<Volunteer> getVolunteerByVolunteerStatusFreeIsTrue();

    List<Volunteer> getVolunteerByVolunteerStatusFreeIsFalse();
}
