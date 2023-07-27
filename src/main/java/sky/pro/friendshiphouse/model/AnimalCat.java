package sky.pro.friendshiphouse.model;

import lombok.*;
import sky.pro.friendshiphouse.constant.AnimalDogKind;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Getter
@Setter
public class AnimalCat {
    @Id
    @GeneratedValue
    private long animalCatId;
    private String animalCatName;
    private int animalCatAge;
    private String animalCatBreed;
    private String animalCatInfo;
    private AnimalDogKind animalCatKind;  //котенок; взрослый; с особенностями по зрению и передвижению(ENUM)
    private boolean animalCatStatusFree; // занят; свободен
    @OneToOne
    @JoinColumn(name = "adopter_id")
    private Adopter adopter;

    public AnimalCat() {}
    public AnimalCat(long animalCatId,
                     String animalCatName,
                     int animalCatAge,
                     String animalCatBreed,
                     String animalCatInfo,
                     AnimalDogKind animalCatKind) {
        this.animalCatId = animalCatId;
        this.animalCatName = animalCatName;
        this.animalCatAge = animalCatAge;
        this.animalCatBreed = animalCatBreed;
        this.animalCatInfo = animalCatInfo;
        this.animalCatKind = animalCatKind;
        this.animalCatStatusFree = true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AnimalCat animalCat = (AnimalCat) o;
        return animalCatId == animalCat.animalCatId && animalCatAge == animalCat.animalCatAge && animalCatName.equals(animalCat.animalCatName) && animalCatBreed.equals(animalCat.animalCatBreed) && animalCatInfo.equals(animalCat.animalCatInfo);
    }
    @Override
    public int hashCode() {return Objects.hash(animalCatId, animalCatName, animalCatAge, animalCatBreed, animalCatInfo);}

    @Override
    public String toString() {
        return "Кошка " +
                "Id: " + animalCatId +
                ", Кличка: " + animalCatName +
                ", Возраст: " + animalCatAge +
                ", Порода: " + animalCatBreed +
                ", Дополнительная информация: " + animalCatInfo +
                ", Особенности: " + animalCatKind.getDescription() +
                ", Статус: " + animalCatStatusFree +
                ", Усыновитель Id: " + adopter.getAdopterId();
    }
}
