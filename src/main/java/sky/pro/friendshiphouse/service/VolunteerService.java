package sky.pro.friendshiphouse.service;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import sky.pro.friendshiphouse.exception.FormatNotComplianceException;
import sky.pro.friendshiphouse.exception.ObjectAbsenceException;
import sky.pro.friendshiphouse.exception.ObjectAlreadyExistsException;
import sky.pro.friendshiphouse.model.Volunteer;
import sky.pro.friendshiphouse.repository.VolunteerRepository;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;


@RequiredArgsConstructor
@Service
public class VolunteerService {
    private final VolunteerRepository volunteerRepository;
    private final Logger logger = LoggerFactory.getLogger(VolunteerService.class);

    /**
     * Позволяет вывести список всех волонтеров из БД
     *
     * @return список всех волонтеров из БД
     */
    public Collection<Volunteer> getAllVolunteer() {
        logger.info("launching the getAllVolunteer method");
        return volunteerRepository.findAll();
    }

    /**
     * Позволяет найти волонтера по его полю volunteerId
     *
     * @param volunteerId идентификатор волонтера (<b>не</b> может быть <b>null</b>)
     * @return данные по найденному волонтеру
     */
    public Volunteer getVolunteerById(long volunteerId) {
        logger.info("launching the getVolunteerById method with volunteerId: {}", volunteerId);
        if (volunteerRepository.findByVolunteerId(volunteerId) == null) {
            throw new ObjectAbsenceException("Волонтер с таким volunteerId не найден в БД");
        }
        return volunteerRepository.findByVolunteerId(volunteerId);
    }

    /**
     * Позволяет добавить данные по "волонтеру" в БД
     * <br> Использован метод репозитория {@link org.springframework.data.jpa.repository.JpaRepository#save(Object)}
     * <br> Проводится проверка поля volunteerChatId - оно должно состоять из 10ти цифр
     * <br> Проводится проверка волонтеров уже существующих в БД по полю volunteerChatId - что бы не было задвоения
     *
     * @param newVolunteer (необходимо заполнить все поля кроме volunteerId - проставляется БД автоматически)
     */
    public Volunteer createVolunteer(Volunteer newVolunteer) {
        logger.info("launching the createVolunteer method");
        if (newVolunteer.getVolunteerChatId().toString().length() != 10) {
            throw new FormatNotComplianceException("Поле volunteerChatId должно состоять из 10 цифр");
        }
        Long newVolunteerChatId = newVolunteer.getVolunteerChatId();
        List<Long> volunteerChatIds = volunteerRepository.findAll().stream().map(Volunteer::getVolunteerChatId).collect(Collectors.toList());
        volunteerChatIds.forEach(volunteerChatId -> {
            if (newVolunteerChatId.equals(volunteerChatId)) {
                throw new ObjectAlreadyExistsException("Волонтер с таким volunteerChatId уже существует в БД");
            }
        });
        return volunteerRepository.save(newVolunteer);
    }

    /**
     * Позволяет изменить данные по волонтеру, если правильно указано поле volunteerId
     * <br> Использован метод репозитория {@link org.springframework.data.jpa.repository.JpaRepository#save(Object)}
     * <br> Проводится проверка поля adopterChatId - оно должно состоять из 10ти цифр
     *
     * @param volunteer (важно правильно заполнить поле <b>volunteerId</b>)
     */
    public Volunteer editVolunteer(Volunteer volunteer) {
        logger.info("launching the editVolunteer method");
        if (volunteerRepository.findByVolunteerId(volunteer.getVolunteerId()) == null) {
            throw new ObjectAbsenceException("Волонтер с таким volunteerId не найден в БД");
        } else if (volunteer.getVolunteerChatId().toString().length() != 10) {
            throw new FormatNotComplianceException("Поле volunteerChatId должно состоять из 10 цифр");
        }
        return volunteerRepository.save(volunteer);
    }

    /**
     * Позволяет сменить статус волонтеру (занят/свободен)
     *
     * @param volunteerId         идентификатор волонтера (<b>не</b> может быть <b>null</b>)
     * @param volunteerStatusFree (true=свободен/ false=занят)
     * @return данные по волонтеру с измененным статусом
     */
    public Volunteer editVolunteerStatus(long volunteerId, boolean volunteerStatusFree) {
        logger.info("launching the editVolunteerStatus method with volunteerId: {}", volunteerId);
        Volunteer volunteer = volunteerRepository.findByVolunteerId(volunteerId);
        if (volunteer == null) {
            throw new ObjectAbsenceException("Волонтер с таким volunteerId не найден в БД");
        }
        volunteer.setVolunteerStatusFree(volunteerStatusFree);
        return volunteerRepository.save(volunteer);
    }

    /**
     * Позволяет удалить волонтера из БД
     * <br> Использован метод репозитория {@link org.springframework.data.jpa.repository.JpaRepository#deleteById(Object)}
     *
     * @param volunteerId идентификатор волонтера (<b>не</b> может быть <b>null</b>)
     */
    public void deleteVolunteer(long volunteerId) {
        logger.info("launching the deleteVolunteer method with volunteerId: {}", volunteerId);
        if (volunteerRepository.findByVolunteerId(volunteerId) == null) {
            throw new ObjectAbsenceException("Волонтер с таким volunteerId не найден в БД");
        }
        volunteerRepository.deleteById(volunteerId);
    }

    /**
     * Метод выбирает одного волонтера из списка свободных волонтеров (если нет свободных адресует запрос администратору)
     *
     * @return свободного волонтера
     */
    public Volunteer callVolunteer() {
        List<Volunteer> volunteersFree = volunteerRepository.getVolunteerByVolunteerStatusFreeIsTrue();
        if (!volunteersFree.isEmpty()) {
            return volunteersFree.stream().findFirst().get();
        }//из списка берем 1 волонтера, сейчас это всегда первый из списка(может быть переделать что бы выбирать рандомно)?
        List<Volunteer> volunteers = volunteerRepository.getVolunteerByVolunteerStatusFreeIsFalse();
        return volunteers.stream().findFirst().get(); //первый волонтер из БД - это администратор и у него всегда статус false, если нет свободных волонтеров запрос попадет к нему и самостоятельно решит что с этим сделать
    }
}
