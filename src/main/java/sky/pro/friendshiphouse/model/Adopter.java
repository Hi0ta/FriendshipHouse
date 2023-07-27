package sky.pro.friendshiphouse.model;

import lombok.*;

import javax.persistence.*;
import java.util.Collection;
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
    @OneToOne
    @JoinColumn(name = "animal_dog_id")
    private AnimalDog animalDog;
    @OneToOne
    @JoinColumn(name = "animal_cat_id")
    private AnimalCat animalCat;
    @OneToMany(mappedBy = "adopter")
    private Collection<Report> report;
    private boolean adopterStatusBlackList;  // true=заблокирован(не прошел испытательный срок) false=нет в черном списке/не заблокирован

    public Adopter() {}
    public Adopter(long adopterId,
                   Long adopterChatId,
                   String adopterLastname,
                   String adopterFirstname,
                   String adopterMiddleName,
                   String adopterPassport,
                   String adopterTelNumber,
                   String adopterAddress,
                   AnimalDog animalDog) {
        this.adopterId = adopterId;
        this.adopterChatId = adopterChatId;
        this.adopterLastname = adopterLastname;
        this.adopterFirstname = adopterFirstname;
        this.adopterMiddleName = adopterMiddleName;
        this.adopterPassport = adopterPassport;
        this.adopterTelNumber = adopterTelNumber;
        this.adopterAddress = adopterAddress;
        this.animalDog = animalDog;
        this.adopterStatusBlackList = false;
    }

    public Adopter(long adopterId,
                   Long adopterChatId,
                   String adopterLastname,
                   String adopterFirstname,
                   String adopterMiddleName,
                   String adopterPassport,
                   String adopterTelNumber,
                   String adopterAddress,
                   AnimalCat animalCat) {
        this.adopterId = adopterId;
        this.adopterChatId = adopterChatId;
        this.adopterLastname = adopterLastname;
        this.adopterFirstname = adopterFirstname;
        this.adopterMiddleName = adopterMiddleName;
        this.adopterPassport = adopterPassport;
        this.adopterTelNumber = adopterTelNumber;
        this.adopterAddress = adopterAddress;
        this.animalCat = animalCat;
        this.adopterStatusBlackList = false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Adopter adopter = (Adopter) o;
        return adopterId == adopter.adopterId && adopterChatId.equals(adopter.adopterChatId) && adopterLastname.equals(adopter.adopterLastname) && adopterFirstname.equals(adopter.adopterFirstname) && adopterMiddleName.equals(adopter.adopterMiddleName) && adopterPassport.equals(adopter.adopterPassport) && adopterTelNumber.equals(adopter.adopterTelNumber) && adopterAddress.equals(adopter.adopterAddress);
    }
    @Override
    public int hashCode() {return Objects.hash(adopterId, adopterChatId, adopterLastname, adopterFirstname, adopterMiddleName, adopterPassport, adopterTelNumber, adopterAddress);}

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
                ", animalDog: " + animalDog.getAnimalDogId() +
                ", animalCat: " + animalCat.getAnimalCatId() +
                ", adopterStatusBlackList = " + adopterStatusBlackList;}
}
