package sky.pro.friendshiphouse.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Collection;

/**
 * Усыновитель
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
//@Table(name = "adopter")
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
}
