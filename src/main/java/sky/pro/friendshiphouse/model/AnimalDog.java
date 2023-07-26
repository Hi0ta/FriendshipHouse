package sky.pro.friendshiphouse.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import sky.pro.friendshiphouse.constant.AnimalDogKind;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
//@Table(name = "animal_dog")
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
    @JoinColumn(name = "adopter_id")
    private Adopter adopter;
}
