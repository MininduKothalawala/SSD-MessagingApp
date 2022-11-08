package messagingapp.ssd.Models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("User")
public class User {

    @Id
    private String Id;
    private String Username;
    private String Password;
    private String Email;
    private String Role;
    private String EncryptionKey;

    public User() {
    }

    public User(String id, String username, String password, String email, String role, String encryptionKey) {
        Id = id;
        Username = username;
        Password = password;
        Email = email;
        Role = role;
        EncryptionKey = encryptionKey;
    }

    public String getEncryptionKey() {
        return EncryptionKey;
    }

    public void setEncryptionKey(String encryptionKey) {
        EncryptionKey = encryptionKey;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getRole() {
        return Role;
    }

    public void setRole(String role) {
        Role = role;
    }
}
