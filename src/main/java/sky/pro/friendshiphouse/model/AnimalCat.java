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
//@Table(name = "animal_cat")
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
}
