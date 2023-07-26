package sky.pro.friendshiphouse.repository;

import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sky.pro.friendshiphouse.model.Adopter;

import java.util.List;

@Repository
public interface AdopterRepository extends JpaRepository<Adopter, Long> {
    Adopter findByAdopterId(long adopterId);

    Adopter findByAdopterChatId(Long adopterChatId);
    @NotNull
    List<Adopter> findAll();

    List<Adopter> getAdopterByAdopterStatusBlackList(boolean statusBlackList);
}
