package sky.pro.friendshiphouse.model;

import javax.persistence.*;
import java.util.Collection;
import java.util.Objects;

/**
 * Усыновитель
 */
@Entity
public class Adopter {

    @Id
    @GeneratedValue
    private long adopterId;
    private Integer adopterChatId;
    private String adopterLastname;
    private String adopterFirstname;
    private String adopterMiddlename;
    private String adopterPassport;
    private String adopterTelNumber;
    private String adopterAddress;
    @OneToOne
    private AnimalDog animalDog;
    @OneToMany(mappedBy = "adopter")
    private Collection<Report> report;

    //private boolean adopterStatus;  // возможно понадобится для черного списка

    public long getAdopterId() {
        return adopterId;
    }

    public void setAdopterId(long adopterId) {
        this.adopterId = adopterId;
    }

    public String getAdopterLastname() {
        return adopterLastname;
    }

    public void setAdopterLastname(String adopterLastname) {
        this.adopterLastname = adopterLastname;
    }

    public String getAdopterFirstname() {
        return adopterFirstname;
    }

    public void setAdopterFirstname(String adopterFirstname) {
        this.adopterFirstname = adopterFirstname;
    }

    public String getAdopterMiddlename() {
        return adopterMiddlename;
    }

    public void setAdopterMiddlename(String adopterMiddlename) {
        this.adopterMiddlename = adopterMiddlename;
    }

    public String getAdopterPassport() {
        return adopterPassport;
    }

    public void setAdopterPassport(String adopterPassport) {
        this.adopterPassport = adopterPassport;
    }

    public Integer getAdopterChatId() {
        return adopterChatId;
    }

    public void setAdopterChatId(Integer adopterChatId) {
        this.adopterChatId = adopterChatId;
    }

    public String getAdopterTelNumber() {
        return adopterTelNumber;
    }

    public void setAdopterTelNumber(String adopterTelNumber) {
        this.adopterTelNumber = adopterTelNumber;
    }

    public String getAdopterAddress() {
        return adopterAddress;
    }

    public void setAdopterAddress(String adopterAddress) {
        this.adopterAddress = adopterAddress;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Adopter adopter = (Adopter) o;
        return adopterId == adopter.adopterId && Objects.equals(adopterChatId, adopter.adopterChatId) && Objects.equals(adopterLastname, adopter.adopterLastname) && Objects.equals(adopterFirstname, adopter.adopterFirstname) && Objects.equals(adopterMiddlename, adopter.adopterMiddlename) && Objects.equals(adopterPassport, adopter.adopterPassport) && Objects.equals(adopterTelNumber, adopter.adopterTelNumber) && Objects.equals(adopterAddress, adopter.adopterAddress) && Objects.equals(animalDog, adopter.animalDog) && Objects.equals(report, adopter.report);
    }

    @Override
    public int hashCode() {
        return Objects.hash(adopterId, adopterChatId, adopterLastname, adopterFirstname, adopterMiddlename, adopterPassport, adopterTelNumber, adopterAddress, animalDog, report);
    }

    @Override
    public String toString() {
        return "Adopter{" +
                "adopterId=" + adopterId +
                ", adopterLastname='" + adopterLastname + '\'' +
                ", adopterFirstname='" + adopterFirstname + '\'' +
                ", adopterMiddlename='" + adopterMiddlename + '\'' +
                ", adopterPassport='" + adopterPassport + '\'' +
                ", adopterChatId='" + adopterChatId + '\'' +
                ", adopterTelNumber='" + adopterTelNumber + '\'' +
                ", adopterAddress='" + adopterAddress + '\'' +
                '}';
    }
}
