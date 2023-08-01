package sky.pro.friendshiphouse;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class FriendshipHouseApplicationTests {

    @LocalServerPort
    private int port;

    @Test
    void contextLoads() {
    }

}
