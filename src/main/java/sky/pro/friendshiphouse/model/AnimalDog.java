package sky.pro.friendshiphouse.model;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class AnimalDog {

    @Id
    @GeneratedValue
    private Long animalDogId;
    private String animalDogName;
    private int animalDogAge;
    private String animalDogBreed;
    private String animalDogInfo;
    private String animalDogKind;  //щенок; взрослый; с особенностями - сделать ENUM
    private boolean animalDogStatus; // занят; свободен

    @OneToOne
    private Adopter adopter;


    public Long getAnimalDogId() {
        return animalDogId;
    }

    public void setAnimalDogId(Long animalDogId) {
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

    public String getAnimalDogKind() {
        return animalDogKind;
    }

    public void setAnimalDogKind(String animalDogKind) {
        this.animalDogKind = animalDogKind;
    }

    public boolean isAnimalDogStatus() {
        return animalDogStatus;
    }

    public void setAnimalDogStatus(boolean animalDogStatus) {
        this.animalDogStatus = animalDogStatus;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AnimalDog animalDog = (AnimalDog) o;
        return animalDogAge == animalDog.animalDogAge && animalDogStatus == animalDog.animalDogStatus && animalDogId.equals(animalDog.animalDogId) && animalDogName.equals(animalDog.animalDogName) && animalDogBreed.equals(animalDog.animalDogBreed) && animalDogInfo.equals(animalDog.animalDogInfo) && animalDogKind.equals(animalDog.animalDogKind);
    }

    @Override
    public int hashCode() {
        return Objects.hash(animalDogId, animalDogName, animalDogAge, animalDogBreed, animalDogInfo, animalDogKind, animalDogStatus);
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
                ", animalDogStatus=" + animalDogStatus +
                '}';
    }
}
