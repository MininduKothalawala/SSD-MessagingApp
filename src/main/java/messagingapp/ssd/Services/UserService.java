package messagingapp.ssd.Services;

import messagingapp.ssd.Models.User;
import messagingapp.ssd.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.spec.SecretKeySpec;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final EncryptionService encryptionService;

    @Autowired
    public UserService(UserRepository userRepository, EncryptionService encryptionService) {
        this.userRepository = userRepository;
        this.encryptionService = encryptionService;
    }

    //Register New User
    public void RegisterUser(User user){
        String key = encryptionService.getSecureRandomKey();
        user.setEncryptionKey(encryptionService.encrypt(key));
        user.setPassword(encryptionService.encrypt(user.getPassword()));
        userRepository.save(user);
    }

    public Boolean AuthenticateUser(String username, String password){
        List<User> users = userRepository.findAll();
        Boolean Authentication = false;
        for (User user : users){
            String decPassword = encryptionService.decrypt(user.getPassword());
            if (user.getUsername().equals(username) && decPassword.equals(password)){
                Authentication = true;
                break;
            }
        }

        return Authentication;
    }

    public UserDetails GetUserByUsername(String username){
        List<User> users = userRepository.findAll();
        User result = new User();

        Boolean Authentication = false;
        for (User user : users){
            if (user.getUsername().equals(username)){
                result = user;
            }
        }

        return new org.springframework.security.core.userdetails.User(result.getUsername(), result.getPassword(), new ArrayList<>());
    }

    public String GetUserRoleByUsername(String username){
        List<User> users = userRepository.findAll();
        String role = null;

        for (User user : users){
            if (user.getUsername().equals(username)){
                role = user.getRole();
            }
        }

        return role;
    }

    public String GetUserEncryptionKey(String username){
        List<User> users = userRepository.findAll();
        String key = null;

        for (User user : users){
            if (user.getUsername().equals(username)){
                key = encryptionService.decrypt(user.getEncryptionKey());
            }
        }

        return key;
    }
}
