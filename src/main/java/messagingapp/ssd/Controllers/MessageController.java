package messagingapp.ssd.Controllers;

import messagingapp.ssd.Config.JwtTokenUtil;
import messagingapp.ssd.Config.SystemConstants;
import messagingapp.ssd.Models.Message;
import messagingapp.ssd.Models.User;
import messagingapp.ssd.Services.FileService;
import messagingapp.ssd.Services.MessageService;
import messagingapp.ssd.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
//@CrossOrigin(origins = "*")
@RequestMapping("/message")
public class MessageController {

    @Autowired
    private FileService fileService;

    @Autowired
    private final JwtTokenUtil jwtTokenUtil;

    @Autowired
    private final UserService userService;

    public SystemConstants systemConstants;

    @Autowired
    private final MessageService messageService;

    public MessageController(JwtTokenUtil jwtTokenUtil, UserService userService, MessageService messageService) {
        this.jwtTokenUtil = jwtTokenUtil;
        this.userService = userService;
        this.messageService = messageService;
    }

    @PostMapping("/addmessage")
    public ResponseEntity<?> AddMessage(@RequestBody Message message, @RequestHeader String Authorization){
        try {
            String token = Authorization.substring(7);
            String username = jwtTokenUtil.getUsernameFromToken(token);
            messageService.SaveMessage(message, username);
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
    public ResponseEntity<?> GetMessagesByUser(@RequestHeader String Authorization){
        try {
            String token = Authorization.substring(7);
            String user = jwtTokenUtil.getUsernameFromToken(token);
            List<Message> messages = messageService.GetMessagesByUser(user);
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
    public ResponseEntity<?> upload(@RequestBody() MultipartFile file, @RequestHeader String Authorization){
        try {
            String token = Authorization.substring(7);
            String username = jwtTokenUtil.getUsernameFromToken(token);
            String role = userService.GetUserRoleByUsername(username);

            if (role != "Manager"){
                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
            }
            return new ResponseEntity<>(fileService.addFile(file), HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
