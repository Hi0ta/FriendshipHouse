package sky.pro.friendshiphouse.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import sky.pro.friendshiphouse.exception.FormatNotComplianceException;
import sky.pro.friendshiphouse.exception.ObjectAbsenceException;
import sky.pro.friendshiphouse.exception.ObjectAlreadyExistsException;
import sky.pro.friendshiphouse.model.Adopter;
import sky.pro.friendshiphouse.repository.AdopterRepository;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AdopterService {
    private final AdopterRepository adopterRepository;
    private final Logger logger = LoggerFactory.getLogger(AdopterService.class);

    public AdopterService(AdopterRepository adopterRepository) {
        this.adopterRepository = adopterRepository;
    }

    /**
     * Позволяет вывести список всех усыновителей из БД
     *
     * @return список всех усыновителей из БД
     */
    public Collection<Adopter> getAllAdopter() {
        logger.info("launching the getAllAdopter method");
        return adopterRepository.findAll();
    }

    /**
     * Позволяет найти усыновителя по его полю adopterId
     *
     * @param adopterId идентификатор усыновителя (<b>не</b> может быть <b>null</b>)
     * @return данные по найденному усыновителю
     */
    public Adopter getAdopterById(long adopterId) {
        logger.info("launching the getAdopterById method with adopterId: {}", adopterId);
        if (adopterRepository.findByAdopterId(adopterId) == null) {
            throw new ObjectAbsenceException("Усыновитель с таким adopterId не найден в БД");
        }
        return adopterRepository.findByAdopterId(adopterId);
    }

    /**
     * Позволяет добавить данные по "усыновителю" в БД
     * <br> Использован метод репозитория {@link org.springframework.data.jpa.repository.JpaRepository#save(Object)}
     * <br> Проводится проверка усыновителей уже существующих в БД по пасспорту - что бы не было задвоения
     * <br> Проводится проверка поля аdopterChatId - оно должно состоять из 10ти цифр
     *
     * @param newAdopter (необходимо заполнить все поля кроме adopterId - проставляется БД автоматически)
     */
    public Adopter createAdopter(Adopter newAdopter) {
        logger.info("launching the createAdopter method");
        if (newAdopter.getAdopterChatId().toString().length() != 10) {
            throw new FormatNotComplianceException("Поле adopterChatId должно состоять из 10 цифр");
        }
        String newAdopterPassport = newAdopter.getAdopterPassport();
        List<String> passports = adopterRepository.findAll().stream().map(Adopter::getAdopterPassport).collect(Collectors.toList());
        passports.forEach(passport -> {
            if (newAdopterPassport.equals(passport)) {
                throw new ObjectAlreadyExistsException("Усыновитель с таким пасспортом уже существует в БД");
            }
        });
        return adopterRepository.save(newAdopter);
    }

    /**
     * Позволяет изменить данные по усыновителю, если правильно указано поле adopterId
     * <br> Использован метод репозитория {@link org.springframework.data.jpa.repository.JpaRepository#save(Object)}
     * <br> Проводится проверка поля аdopterChatId - оно должно состоять из 10ти цифр
     *
     * @param adopter (важно правильно заполнить поле <b>adopterId</b>)
     */
    public Adopter editAdopter(Adopter adopter) {
        logger.info("launching the editAdopter method");
        if (adopterRepository.findByAdopterId(adopter.getAdopterId()) == null) {
            throw new ObjectAbsenceException("Усыновитель с таким adopterId не найден в БД");
        } else if (adopter.getAdopterChatId().toString().length() != 10) {
            throw new FormatNotComplianceException("Поле adopterChatId должно состоять из 10 цифр");
        }
        return adopterRepository.save(adopter);
    }

    /**
     * Позволяет удалить усыновителя из БД
     * <br> Использован метод репозитория {@link org.springframework.data.jpa.repository.JpaRepository#deleteById(Object)}
     *
     * @param adopterId идентификатор усыновителя (<b>не</b> может быть <b>null</b>)
     */
    public void deleteAdopter(long adopterId) {
        logger.info("launching the deleteAdopter method with adopterId: {}", adopterId);
        if (adopterRepository.findByAdopterId(adopterId) == null) {
            throw new ObjectAbsenceException("Усыновитель с таким adopterId не найден в БД");
        }
        adopterRepository.deleteById(adopterId);
    }
}
