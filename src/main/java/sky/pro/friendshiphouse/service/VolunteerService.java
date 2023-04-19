package sky.pro.friendshiphouse.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sky.pro.friendshiphouse.model.Volunteer;
import sky.pro.friendshiphouse.repository.VolunteerRepository;

@Service
public class VolunteerService {

    @Autowired
    private final VolunteerRepository volunteerRepository;

    private final static Logger log = LoggerFactory.getLogger(VolunteerService.class);


    public VolunteerService(VolunteerRepository volunteerRepository) {
        this.volunteerRepository = volunteerRepository;
    }

    /**
     * Позволяет добавить "волонтера" в БД
     * <br> Использован метод репозитория {@link org.springframework.data.jpa.repository.JpaRepository#save(Object)}
     *
     * @param volunteer (необходимо заполнить все поля кроме Id - он проставляется БД автоматически)
     * @return данные по волонтеру добавленному в БД
     */
    public Volunteer createVolunteer(Volunteer volunteer) {
        log.info("launching the createVolunteer method");
        return volunteerRepository.save(volunteer);
    }

    /**
     * Позволяет обновить данные "волонтера" в БД
     * <br> Использован метод репозитория {@link org.springframework.data.jpa.repository.JpaRepository#save(Object)}
     *
     * @param volunteer (необходимо заполнить все поля кроме Id)
     * @return данные по измененному волонтеру
     */
    public Volunteer editVolunteer(Volunteer volunteer) {
        log.info("launching the editVolunteer method");
        return volunteerRepository.save(volunteer);
    }

    /**
     * Позволяет удалить данные по "волонтеру" из БД
     * <br> Использован метод репозитория {@link org.springframework.data.jpa.repository.JpaRepository#deleteById(Object)}
     *
     * @param id идентификатор волонтера (<b>не</b> может быть <b>null</b>)
     */
    public void deleteVolunteer(long id) {
        log.info("launching the deleteVolunteer method with id: {}", id);
        volunteerRepository.deleteById(id);
    }

    /**
     * Позволяет сменить статус волонтеру (занят/свободен)
     * <br> Использованы методы репозитория {@link org.springframework.data.jpa.repository.JpaRepository#findById(Object)}
     * и {@link org.springframework.data.jpa.repository.JpaRepository#save(Object)}
     *
     * @param id     идентификатор волонтера (<b>не</b> может быть <b>null</b>)
     * @param status (true=свободен/ false=занят)
     * @return данные по волонтеру у которого изменили статус
     */
    public Volunteer editVolunteerStatus(long id, boolean status) {
        log.info("launching the editVolunteerStatus method with id: {}", id);
        Volunteer volunteer = volunteerRepository.findById(id);
        volunteer.setVolunteerStatusFree(status);
        return volunteerRepository.save(volunteer);
    }
}
