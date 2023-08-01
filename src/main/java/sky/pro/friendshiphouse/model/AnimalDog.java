package sky.pro.friendshiphouse.model;

import lombok.*;
import sky.pro.friendshiphouse.constant.AnimalDogKind;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Getter
@Setter
public class AnimalDog {
    @Id
    @GeneratedValue
    private long animalDogId;
    private String animalDogName;
    private int animalDogAge;
    private String animalDogBreed;
    private String animalDogInfo;
    @Enumerated(EnumType.STRING)
    private AnimalDogKind animalDogKind;  //щенок; взрослый; с особенностями по зрению и передвижению(ENUM)
    private boolean animalDogStatusFree; // занят; свободен

    public AnimalDog() {
    }

    public AnimalDog(String animalDogName, int animalDogAge, String animalDogBreed, String animalDogInfo, AnimalDogKind animalDogKind) {
        this.animalDogName = animalDogName;
        this.animalDogAge = animalDogAge;
        this.animalDogBreed = animalDogBreed;
        this.animalDogInfo = animalDogInfo;
        this.animalDogKind = animalDogKind;
        this.animalDogStatusFree = true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AnimalDog animalDog = (AnimalDog) o;
        return animalDogId == animalDog.animalDogId && animalDogAge == animalDog.animalDogAge && animalDogName.equals(animalDog.animalDogName) && animalDogBreed.equals(animalDog.animalDogBreed) && animalDogInfo.equals(animalDog.animalDogInfo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(animalDogId, animalDogName, animalDogAge, animalDogBreed, animalDogInfo);
    }

    @Override
    public String toString() {
        return "Собака " +
                "Id: " + animalDogId +
                ", Кличка: " + animalDogName +
                ", Возраст: " + animalDogAge +
                ", Порода: " + animalDogBreed +
                ", Дополнительная информация: " + animalDogInfo +
                ", Особенности: " + animalDogKind.getDescription() +
                ", Статус: " + animalDogStatusFree;
    }
}
