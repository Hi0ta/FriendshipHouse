package sky.pro.friendshiphouse.model;

import sky.pro.friendshiphouse.constant.AnimalDogKind;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class AnimalDog {

    @Id
    @GeneratedValue
    private long animalDogId;
    private String animalDogName;
    private int animalDogAge;
    private String animalDogBreed;
    private String animalDogInfo;
    private AnimalDogKind animalDogKind;  //щенок; взрослый; с особенностями по зрению и передвижению(ENUM)
    private boolean animalDogStatusFree; // занят; свободен

    @OneToOne
    private Adopter adopter;


    public long getAnimalDogId() {
        return animalDogId;
    }

    public void setAnimalDogId(long animalDogId) {
        this.animalDogId = animalDogId;
    }

    public String getAnimalDogName() {
        return animalDogName;
    }

    public void setAnimalDogName(String animalDogName) {
        this.animalDogName = animalDogName;
    }

    public int getAnimalDogAge() {
        return animalDogAge;
    }

    public void setAnimalDogAge(int animalDogAge) {
        this.animalDogAge = animalDogAge;
    }

    public String getAnimalDogBreed() {
        return animalDogBreed;
    }

    public void setAnimalDogBreed(String animalDogBreed) {
        this.animalDogBreed = animalDogBreed;
    }

    public String getAnimalDogInfo() {
        return animalDogInfo;
    }

    public void setAnimalDogInfo(String animalDogInfo) {
        this.animalDogInfo = animalDogInfo;
    }

    public AnimalDogKind getAnimalDogKind() {
        return animalDogKind;
    }

    public void setAnimalDogKind(AnimalDogKind animalDogKind) {
        this.animalDogKind = animalDogKind;
    }

    public boolean isAnimalDogStatusFree() {
        return animalDogStatusFree;
    }

    public void setAnimalDogStatusFree(boolean animalDogStatusFree) {
        this.animalDogStatusFree = animalDogStatusFree;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AnimalDog animalDog = (AnimalDog) o;
        return animalDogId == animalDog.animalDogId && animalDogAge == animalDog.animalDogAge && animalDogStatusFree == animalDog.animalDogStatusFree && Objects.equals(animalDogName, animalDog.animalDogName) && Objects.equals(animalDogBreed, animalDog.animalDogBreed) && Objects.equals(animalDogInfo, animalDog.animalDogInfo) && Objects.equals(animalDogKind, animalDog.animalDogKind) && Objects.equals(adopter, animalDog.adopter);
    }

    @Override
    public int hashCode() {
        return Objects.hash(animalDogId, animalDogName, animalDogAge, animalDogBreed, animalDogInfo, animalDogKind, animalDogStatusFree, adopter);
    }

    @Override
    public String toString() {
        return "AnimalDog{" +
                "animalDogId=" + animalDogId +
                ", animalDogName='" + animalDogName + '\'' +
                ", animalDogAge=" + animalDogAge +
                ", animalDogBreed='" + animalDogBreed + '\'' +
                ", animalDogInfo='" + animalDogInfo + '\'' +
                ", animalDogKind='" + animalDogKind + '\'' +
                ", animalDogStatus=" + animalDogStatusFree +
                '}';
    }
}
