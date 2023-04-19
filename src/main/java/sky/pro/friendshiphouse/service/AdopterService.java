package sky.pro.friendshiphouse.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sky.pro.friendshiphouse.model.Adopter;
import sky.pro.friendshiphouse.repository.AdopterRepository;

@Service
public class AdopterService {

    @Autowired
    private final AdopterRepository adopterRepository;

    private final static Logger log = LoggerFactory.getLogger(AdopterService.class);

    public AdopterService(AdopterRepository adopterRepository) {
        this.adopterRepository = adopterRepository;
    }

    /**
     * Позволяет добавить "усыновителя" в БД
     * <br> Использован метод репозитория {@link org.springframework.data.jpa.repository.JpaRepository#save(Object)}
     *
     * @param adopter (необходимо заполнить все поля кроме Id - он проставляется БД автоматически)
     * @return данные по усыновителю добавленному в БД
     */
    public Adopter createAdopter(Adopter adopter) {
        log.info("launching the createAdopter method");
        return adopterRepository.save(adopter);
    }

    /**
     * Позволяет обновить данные "усыновителя" в БД
     * <br> Использован метод репозитория {@link org.springframework.data.jpa.repository.JpaRepository#save(Object)}
     *
     * @param adopter (необходимо заполнить все поля кроме Id)
     * @return данные по измененному усыновителю
     */
    public Adopter editAdopter(Adopter adopter) {
        log.info("launching the editAdopter method");
        return adopterRepository.save(adopter);
    }

    /**
     * Позволяет удалить данные по "усыновителю" из БД
     * <br> Использован метод репозитория {@link org.springframework.data.jpa.repository.JpaRepository#deleteById(Object)}
     *
     * @param id идентификатор волонтера (<b>не</b> может быть <b>null</b>)
     */
    public void deleteAdopter(long id) {
        log.info("launching the deleteAdopter method with id: {}", id);
        adopterRepository.deleteById(id);
    }
}
