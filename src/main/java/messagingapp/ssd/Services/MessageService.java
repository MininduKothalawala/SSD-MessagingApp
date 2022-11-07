package messagingapp.ssd.Services;

import messagingapp.ssd.Models.Message;
import messagingapp.ssd.Repositories.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MessageService {

    private final MessageRepository messageRepository;
    private final EncryptionService encryptionService;

    @Autowired
    public MessageService(MessageRepository messageRepository, EncryptionService encryptionService) {
        this.messageRepository = messageRepository;
        this.encryptionService = encryptionService;
    }

    //Save Message
    public void SaveMessage(Message message){
        message.setContent(encryptionService.encrypt(message.getContent()));
        messageRepository.save(message);
    }

    //Get All Messages
    public List<Message> GetAllMessages(){
        List<Message> messages = messageRepository.findAll();
        List<Message> finalList = new ArrayList<>();

        for(Message message : messages){
            message.setContent(encryptionService.decrypt(message.getContent()));
            finalList.add(message);
        }

        return messages;
    }

    //Get Messages By UserName
    public List<Message> GetMessagesByUser(String username){
        List<Message> messages = messageRepository.findAll();
        List<Message> finalList = new ArrayList<>();

        for(Message message : messages){
            if(message.getSender().equals(username)){
                message.setContent(encryptionService.decrypt(message.getContent()));
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
