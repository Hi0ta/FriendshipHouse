package sky.pro.friendshiphouse.service;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import sky.pro.friendshiphouse.exception.ObjectAbsenceException;
import sky.pro.friendshiphouse.exception.ObjectAlreadyExistsException;
import sky.pro.friendshiphouse.model.AnimalDog;
import sky.pro.friendshiphouse.repository.AnimalDogRepository;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;


@RequiredArgsConstructor
@Service
public class AnimalDogService {
    private final AnimalDogRepository animalDogRepository;
    private final Logger logger = LoggerFactory.getLogger(AnimalDogService.class);


    /**
     * Позволяет вывести список всех собак из БД
     *
     * @return список всех собак из БД
     */
    public Collection<AnimalDog> getAllAnimalDog() {
        logger.info("launching the getAllAnimalDog method");
        return animalDogRepository.findAll();
    }

    /**
     * Позволяет вывести список всех собак из БД с учетом статуса
     *
     * @param statusFree (true=свободна/ false=занята)
     * @return список всех собак из БД с учетом статуса
     */
    public Collection<AnimalDog> getAnimalDogByAnimalDogStatusFree(boolean statusFree) {
        logger.info("launching the findAnimalDogByAnimalDogStatusFree method");
        return animalDogRepository.getAnimalDogByAnimalDogStatusFree(statusFree);
    }

    /**
     * Позволяет найти собаку по ее полю adopterId
     *
     * @param animalDogId идентификатор собаки (<b>не</b> может быть <b>null</b>)
     * @return данные по найденной собаке
     */
    public AnimalDog getAnimalDogById(long animalDogId) {
        logger.info("launching the getAnimalDogById method with animalDogId: {}", animalDogId);
        if (animalDogRepository.findByAnimalDogId(animalDogId) == null) {
            throw new ObjectAbsenceException("Собака с таким animalDogId не найдена в БД");
        }
        return animalDogRepository.findByAnimalDogId(animalDogId);
    }

    /**
     * Позволяет добавить данные по "собаке" в БД
     * <br> Использован метод репозитория {@link org.springframework.data.jpa.repository.JpaRepository#save(Object)}
     * <br> Проводится проверка собак уже существующих в БД по полю animalDogName - что бы не было задвоения
     *
     * @param newAnimalDog (необходимо заполнить все поля кроме animalDogId - проставляется БД автоматически)
     */
    public AnimalDog createAnimalDog(AnimalDog newAnimalDog) {
        logger.info("launching the createAnimalDog method");
        String newAnimalDogName = newAnimalDog.getAnimalDogName();
        List<String> animalDogNames = animalDogRepository.findAll().stream().map(AnimalDog::getAnimalDogName).collect(Collectors.toList());
        animalDogNames.forEach(animalDogName -> {
            if (newAnimalDogName.equals(animalDogName)) {
                throw new ObjectAlreadyExistsException("Собака с такой кличкой уже существует в БД");
            }
        });
        return animalDogRepository.save(newAnimalDog);
    }

    /**
     * Позволяет изменить данные по собаке, если правильно указано поле animalDogId
     * <br> Использован метод репозитория {@link org.springframework.data.jpa.repository.JpaRepository#save(Object)}
     *
     * @param animalDog (важно правильно заполнить поле <b>animalDogId</b>)
     */
    public AnimalDog editAnimalDog(AnimalDog animalDog) {
        logger.info("launching the editAnimalDog method");
        if (animalDogRepository.findByAnimalDogId(animalDog.getAnimalDogId()) == null) {
            throw new ObjectAbsenceException("Собака с таким animalDogId не найдена в БД");
        }
        return animalDogRepository.save(animalDog);
    }


    /**
     * Позволяет сменить статус собаке (занят/свободен)
     *
     * @param animalDogId         идентификатор собаки (<b>не</b> может быть <b>null</b>)
     * @param animalDogStatusFree (true=свободен/ false=занят)
     * @return данные по собаке с измененным статусом
     */
    public AnimalDog editAnimalDogStatus(long animalDogId, boolean animalDogStatusFree) {
        logger.info("launching the editAnimalDogStatus method with animalDogId: {}", animalDogId);
        AnimalDog animalDog = animalDogRepository.findByAnimalDogId(animalDogId);
        if (animalDog == null) {
            throw new ObjectAbsenceException("Собака с таким animalDogId не найдена в БД");
        }
        animalDog.setAnimalDogStatusFree(animalDogStatusFree);
        return animalDogRepository.save(animalDog);
    }

    /**
     * Позволяет удалить собаку из БД
     * <br> Использован метод репозитория {@link org.springframework.data.jpa.repository.JpaRepository#deleteById(Object)}
     *
     * @param animalDogId идентификатор собаки (<b>не</b> может быть <b>null</b>)
     */
    public void deleteAnimalDog(long animalDogId) {
        logger.info("launching the deleteAnimalDog method with animalDogId: {}", animalDogId);
        if (animalDogRepository.findByAnimalDogId(animalDogId) == null) {
            throw new ObjectAbsenceException("Собака с таким animalDogId не найдена в БД");
        }
        animalDogRepository.deleteById(animalDogId);
    }
}
