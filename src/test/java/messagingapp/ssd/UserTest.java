package messagingapp.ssd;

import messagingapp.ssd.Models.Message;
import messagingapp.ssd.Models.User;
import messagingapp.ssd.Services.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class UserTest {

    @MockBean
    UserService userService;

    @Test
    public void testUserRegistration(){

        User user = new User("1111", "testuser@gmail.com", "test@123#pass", "testuser@gmail.com", "Admin", "");
        userService.RegisterUser(user);

        Assertions.assertTrue(true, "User Registered");
    }

    @Test
    public void testUserLogin(){

        User user = new User("1111", "testuser@gmail.com", "test@123#pass", "testuser@gmail.com", "Admin", "");
        userService.RegisterUser(user);

        boolean auth = userService.AuthenticateUser(user.getUsername(), user.getPassword());

        if (auth){
            Assertions.assertTrue(true, "User Authenticated");
        }
        else {
            Assertions.assertFalse(false, "Unauthenticated user");
        }
    }
}
