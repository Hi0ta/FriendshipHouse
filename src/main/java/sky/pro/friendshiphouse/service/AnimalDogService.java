package sky.pro.friendshiphouse.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sky.pro.friendshiphouse.model.AnimalDog;
import sky.pro.friendshiphouse.repository.AnimalDogRepository;

@Service
public class AnimalDogService {

    @Autowired
    private final AnimalDogRepository animalDogRepository;

    private final static Logger log = LoggerFactory.getLogger(AnimalDogService.class);


    public AnimalDogService(AnimalDogRepository animalDogRepository) {
        this.animalDogRepository = animalDogRepository;
    }

    /**
     * Позволяет добавить "собаку" в БД
     * <br> Использован метод репозитория {@link org.springframework.data.jpa.repository.JpaRepository#save(Object)}
     *
     * @param animalDog (необходимо заполнить все поля кроме Id - он проставляется БД автоматически)
     * @return данные по собаке добавленной в БД
     */
    public AnimalDog createAnimalDog(AnimalDog animalDog) {
        log.info("launching the createAnimalDog method");
        return animalDogRepository.save(animalDog);
    }

    /**
     * Позволяет обновить данные "собаки" в БД
     * <br> Использован метод репозитория {@link org.springframework.data.jpa.repository.JpaRepository#save(Object)}
     *
     * @param animalDog (необходимо заполнить все поля кроме Id)
     * @return данные по измененной собаке
     */
    public AnimalDog editAnimalDog(AnimalDog animalDog) {
        log.info("launching the editAnimalDog method");
        return animalDogRepository.save(animalDog);
    }

    /**
     * Позволяет удалить данные по "собаке" из БД
     * <br> Использован метод репозитория {@link org.springframework.data.jpa.repository.JpaRepository#deleteById(Object)}
     *
     * @param id идентификатор собаки (<b>не</b> может быть <b>null</b>)
     */
    public void deleteAnimalDog(long id) {
        log.info("launching the deleteAnimalDog method with id: {}", id);
        animalDogRepository.deleteById(id);
    }

    /**
     * Позволяет сменить статус собаке (занят/свободен)
     * <br> Использованы методы репозитория {@link org.springframework.data.jpa.repository.JpaRepository#findById(Object)}
     * и {@link org.springframework.data.jpa.repository.JpaRepository#save(Object)}
     *
     * @param id     идентификатор собаки (<b>не</b> может быть <b>null</b>)
     * @param status (true=свободен/ false=занят)
     * @return данные по собаке у которой изменили статус
     */
    public AnimalDog editAnimalDogStatus(long id, boolean status) {
        log.info("launching the editAnimalDogStatus method with id: {}", id);
        AnimalDog animalDog = animalDogRepository.findById(id);
        animalDog.setAnimalDogStatus(status);
        return animalDogRepository.save(animalDog);
    }
}
