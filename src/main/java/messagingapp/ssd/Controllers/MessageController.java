package messagingapp.ssd.Controllers;

import messagingapp.ssd.Models.Message;
import messagingapp.ssd.Services.FileService;
import messagingapp.ssd.Services.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
//@CrossOrigin(origins = "*")
@RequestMapping("/message")
public class MessageController {

    @Autowired
    private FileService fileService;

    @Autowired
    private final MessageService messageService;

    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @PostMapping("/addmessage")
    public ResponseEntity<?> AddMessage(@RequestBody Message message){
        try {
            messageService.SaveMessage(message);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/getallmessage")
    public ResponseEntity<?> GetAllMessages(){
        try {
            List<Message> messages = messageService.GetAllMessages();
            return new ResponseEntity<>(messages, HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/getmessagesbyuser")
    public ResponseEntity<?> GetMessagesByUser(@RequestParam String username){
        try {
            List<Message> messages = messageService.GetMessagesByUser(username);
            return new ResponseEntity<>(messages, HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/getpublicmessage")
    public ResponseEntity<?> GetPublicMessages(){
        try {
            List<Message> messages = messageService.GetPublicMessages();
            return new ResponseEntity<>(messages, HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }


    @PostMapping("/upload")
    public ResponseEntity<?> upload(@RequestParam("file") MultipartFile file){
        try {
//            String jwtToken = requestTokenHeader.substring(7);
//            String username = jwtTokenUtil.getUsernameFromToken(jwtToken);
            return new ResponseEntity<>(fileService.addFile(file), HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
