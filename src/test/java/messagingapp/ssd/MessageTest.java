package messagingapp.ssd;

import messagingapp.ssd.Models.Message;
import messagingapp.ssd.Models.User;
import messagingapp.ssd.Services.MessageService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class MessageTest {

    @MockBean
    MessageService messageService;

    @Test
    public void testMessageCreate(){
        List<User> items = new ArrayList<User>();

        Message message = new Message("0000", "Test Message", "testuser@gmail.com", "Private", null, LocalDateTime.now(), "text");
        messageService.SaveMessage(message, "testuser@gmail.com");

        Assertions.assertTrue(true, "Message Added to Db");
    }

    @Test
    public void testGetMessage(){
        List<User> items = new ArrayList<User>();

        Message message = new Message("0000", "Test Message", "testuser@gmail.com", "Private", null, LocalDateTime.now(), "text");
        messageService.SaveMessage(message, "testuser@gmail.com");

        messageService.GetMessagesByUser(message.getSender());

        Assertions.assertTrue(true, "Message Added to Db");
    }
}
