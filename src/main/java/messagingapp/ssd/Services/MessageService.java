package messagingapp.ssd.Services;

import messagingapp.ssd.Models.Message;
import messagingapp.ssd.Repositories.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class MessageService {

    private final MessageRepository messageRepository;
    private final EncryptionService encryptionService;
    private final UserService userService;

    @Autowired
    public MessageService(MessageRepository messageRepository, EncryptionService encryptionService, UserService userService) {
        this.messageRepository = messageRepository;
        this.encryptionService = encryptionService;
        this.userService = userService;
    }

    //Save Message
    public void SaveMessage(Message message, String username){
        String encKey = userService.GetUserEncryptionKey(username);
        message.setContent(encryptionService.customEncrypt(message.getContent(), encKey));
        message.setSentTime(LocalDateTime.now());
        messageRepository.save(message);
    }

    //Get All Messages
    public List<Message> GetAllMessages(){
        List<Message> messages = messageRepository.findAll();
        List<Message> finalList = new ArrayList<>();

        for(Message message : messages){
            finalList.add(message);
        }

        return messages;
    }

    //Get Messages By UserName
    public List<Message> GetMessagesByUser(String username){
        List<Message> messages = messageRepository.findAll();
        List<Message> finalList = new ArrayList<>();
        String encKey = userService.GetUserEncryptionKey(username);

        for(Message message : messages){
            if(message.getSender().equals(username)){
                message.setContent(encryptionService.customDecrypt(message.getContent(), encKey));
                finalList.add(message);
            }
        }

        return finalList;
    }

    //Get All Public Messages
    public List<Message> GetPublicMessages(){
        List<Message> messages = messageRepository.findAll();
        List<Message> finalList = new ArrayList<>();

        for(Message message : messages){
            if(message.getAudienceType().equals("Public")){
                message.setContent(encryptionService.decrypt(message.getContent()));
                finalList.add(message);
            }
        }

        return finalList;
    }
}
