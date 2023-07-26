package sky.pro.friendshiphouse.service;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import sky.pro.friendshiphouse.exception.ObjectAbsenceException;
import sky.pro.friendshiphouse.exception.ObjectAlreadyExistsException;
import sky.pro.friendshiphouse.model.AnimalCat;
import sky.pro.friendshiphouse.repository.AnimalCatRepository;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;


@RequiredArgsConstructor
@Service
public class AnimalCatService {
    private final AnimalCatRepository animalCatRepository;
    private final Logger logger = LoggerFactory.getLogger(AnimalCatService.class);

    /**
     * Позволяет вывести список всех кошек из БД
     *
     * @return список всех кошек из БД
     */
    public Collection<AnimalCat> getAllAnimalCat() {
        logger.info("launching the getAllAnimalCat method");
        return animalCatRepository.findAll();
    }

    /**
     * Позволяет вывести список всех кошек из БД с учетом статуса
     * @param statusFree (true=свободна/ false=занята)
     * @return список всех кошек из БД с учетом статуса
     */
    public Collection<AnimalCat> getAnimalCatByAnimalCatStatusFree(boolean statusFree) {
        logger.info("launching the getAnimalCatByAnimalCatStatusFree method");
        return animalCatRepository.getAnimalCatByAnimalCatStatusFree(statusFree);
    }

    /**
     * Позволяет найти кошку по ее полю adopterId
     *
     * @param animalCatId идентификатор кошки (<b>не</b> может быть <b>null</b>)
     * @return данные по найденной кошке
     */
    public AnimalCat getAnimalCatById(long animalCatId) {
        logger.info("launching the getAnimalCatById method with animalCatId: {}", animalCatId);
        if (animalCatRepository.findByAnimalCatId(animalCatId) == null) {
            throw new ObjectAbsenceException("Кошка с таким animalCatId не найдена в БД");
        }
        return animalCatRepository.findByAnimalCatId(animalCatId);
    }

    /**
     * Позволяет добавить данные по "кошке" в БД
     * <br> Использован метод репозитория {@link org.springframework.data.jpa.repository.JpaRepository#save(Object)}
     * <br> Проводится проверка кошек уже существующих в БД по полю animalCatName - что бы не было задвоения
     *
     * @param newAnimalCat (необходимо заполнить все поля кроме animalCatId - проставляется БД автоматически)
     */
    public AnimalCat createAnimalCat(AnimalCat newAnimalCat) {
        logger.info("launching the createAnimalCat method");
        String newAnimalCatName = newAnimalCat.getAnimalCatName();
        List<String> animalCatNames = animalCatRepository.findAll().stream().map(AnimalCat::getAnimalCatName).collect(Collectors.toList());
        animalCatNames.forEach(animalCatName -> {
            if (newAnimalCatName.equals(animalCatName)) {
                throw new ObjectAlreadyExistsException("Кошка с такой кличкой уже существует в БД");
            }
        });
        return animalCatRepository.save(newAnimalCat);
    }

    /**
     * Позволяет изменить данные по кошке, если правильно указано поле animalCatId
     * <br> Использован метод репозитория {@link org.springframework.data.jpa.repository.JpaRepository#save(Object)}
     *
     * @param animalCat (важно правильно заполнить поле <b>animalCatId</b>)
     */
    public AnimalCat editAnimalCat(AnimalCat animalCat) {
        logger.info("launching the editAnimalCat method");
        if (animalCatRepository.findByAnimalCatId(animalCat.getAnimalCatId()) == null) {
            throw new ObjectAbsenceException("Кошка с таким animalCatId не найдена в БД");
        }
        return animalCatRepository.save(animalCat);
    }


    /**
     * Позволяет сменить статус кошке (занята/свободна)
     *
     * @param animalCatId идентификатор кошки (<b>не</b> может быть <b>null</b>)
     * @param animalCatStatusFree      (true=свободна/ false=занята)
     * @return данные по кошке с измененным статусом
     */
    public AnimalCat editAnimalCatStatus(long animalCatId, boolean animalCatStatusFree) {
        logger.info("launching the editAnimalDogStatus method with animalDogId: {}", animalCatId);
        AnimalCat animalCat = animalCatRepository.findByAnimalCatId(animalCatId);
        if (animalCat == null) {
            throw new ObjectAbsenceException("Кошка с таким animalCatId не найдена в БД");
        }
        animalCat.setAnimalCatStatusFree(animalCatStatusFree);
        return animalCatRepository.save(animalCat);
    }

    /**
     * Позволяет удалить кошку из БД
     * <br> Использован метод репозитория {@link org.springframework.data.jpa.repository.JpaRepository#deleteById(Object)}
     *
     * @param animalCatId идентификатор кошки (<b>не</b> может быть <b>null</b>)
     */
    public void deleteAnimalCat(long animalCatId) {
        logger.info("launching the deleteAnimalCat method with animalCatId: {}", animalCatId);
        if (animalCatRepository.findByAnimalCatId(animalCatId) == null) {
            throw new ObjectAbsenceException("Кошка с таким animalCatId не найдена в БД");
        }
        animalCatRepository.deleteById(animalCatId);
    }
}
