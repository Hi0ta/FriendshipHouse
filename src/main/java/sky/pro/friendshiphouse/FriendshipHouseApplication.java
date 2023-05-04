package sky.pro.friendshiphouse;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition
public class FriendshipHouseApplication {

    public static void main(String[] args) {
        SpringApplication.run(FriendshipHouseApplication.class, args);
    }

}
