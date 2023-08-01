package sky.pro.friendshiphouse.model;

import lombok.*;

import javax.persistence.*;
import java.util.Objects;

/**
 * Усыновитель
 */
@Entity
@Getter
@Setter
public class Adopter {

    @Id
    @GeneratedValue
    private long adopterId;
    private Long adopterChatId;
    private String adopterLastname;
    private String adopterFirstname;
    private String adopterMiddleName;
    private String adopterPassport;
    private String adopterTelNumber;
    private String adopterAddress;
    private boolean adopterStatusBlackList;  // true=заблокирован(не прошел испытательный срок) false=нет в черном списке/не заблокирован
    @OneToOne
    @JoinColumn(name = "animal_cat_id")
    private AnimalCat animalCat;
    @OneToOne
    @JoinColumn(name = "animal_dog_id")
    private AnimalDog animalDog;

    public Adopter() {
    }

    public Adopter(Long adopterChatId, String adopterLastname, String adopterFirstname, String adopterMiddleName, String adopterPassport, String adopterTelNumber, String adopterAddress, AnimalCat animalCat) {
        this.adopterChatId = adopterChatId;
        this.adopterLastname = adopterLastname;
        this.adopterFirstname = adopterFirstname;
        this.adopterMiddleName = adopterMiddleName;
        this.adopterPassport = adopterPassport;
        this.adopterTelNumber = adopterTelNumber;
        this.adopterAddress = adopterAddress;
        this.adopterStatusBlackList = false;
        this.animalCat = animalCat;
    }

    public Adopter(Long adopterChatId, String adopterLastname, String adopterFirstname, String adopterMiddleName, String adopterPassport, String adopterTelNumber, String adopterAddress, AnimalDog animalDog) {
        this.adopterChatId = adopterChatId;
        this.adopterLastname = adopterLastname;
        this.adopterFirstname = adopterFirstname;
        this.adopterMiddleName = adopterMiddleName;
        this.adopterPassport = adopterPassport;
        this.adopterTelNumber = adopterTelNumber;
        this.adopterAddress = adopterAddress;
        this.adopterStatusBlackList = false;
        this.animalDog = animalDog;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Adopter adopter = (Adopter) o;
        return adopterId == adopter.adopterId && adopterChatId.equals(adopter.adopterChatId) && adopterLastname.equals(adopter.adopterLastname) && adopterFirstname.equals(adopter.adopterFirstname) && adopterMiddleName.equals(adopter.adopterMiddleName) && adopterPassport.equals(adopter.adopterPassport) && adopterTelNumber.equals(adopter.adopterTelNumber) && adopterAddress.equals(adopter.adopterAddress);
    }

    @Override
    public int hashCode() {
        return Objects.hash(adopterId, adopterChatId, adopterLastname, adopterFirstname, adopterMiddleName, adopterPassport, adopterTelNumber, adopterAddress);
    }

    @Override
    public String toString() {
        return "Усыновитель " +
                "Id:" + adopterId +
                ", adopterChatId=" + adopterChatId +
                ", Имя: " + adopterLastname +
                ", Фамилия: " + adopterFirstname +
                ", Отчество: " + adopterMiddleName +
                ", Паспорт: " + adopterPassport +
                ", Тел: " + adopterTelNumber +
                ", Адрес: " + adopterAddress +
                ", adopterStatusBlackList = " + adopterStatusBlackList;
    }
}
