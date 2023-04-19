package sky.pro.friendshiphouse.model;

import javax.persistence.*;
import java.util.Arrays;
import java.util.Objects;

@Entity
public class Home {

    @Id
    @GeneratedValue
    private Long homeId;
    private String homeName;
    private String homeInfo;
    private byte[] homeScheme;

    public Long getHomeId() {
        return homeId;
    }

    public void setHomeId(Long homeId) {
        this.homeId = homeId;
    }

    public String getHomeName() {
        return homeName;
    }

    public void setHomeName(String homeName) {
        this.homeName = homeName;
    }

    public String getHomeInfo() {
        return homeInfo;
    }

    public void setHomeInfo(String homeInfo) {
        this.homeInfo = homeInfo;
    }

    public byte[] getHomeScheme() {
        return homeScheme;
    }

    public void setHomeScheme(byte[] homeScheme) {
        this.homeScheme = homeScheme;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Home home = (Home) o;
        return homeId.equals(home.homeId) && homeName.equals(home.homeName) && homeInfo.equals(home.homeInfo) && Arrays.equals(homeScheme, home.homeScheme);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(homeId, homeName, homeInfo);
        result = 31 * result + Arrays.hashCode(homeScheme);
        return result;
    }

    @Override
    public String toString() {
        return "Home{" +
                "homeId=" + homeId +
                ", homeName='" + homeName + '\'' +
                ", homeInfo='" + homeInfo + '\'' +
                ", homeScheme=" + Arrays.toString(homeScheme) +
                '}';
    }
}
