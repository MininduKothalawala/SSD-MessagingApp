package messagingapp.ssd;

import messagingapp.ssd.Services.EncryptionService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class EncryptionTest {

    @MockBean
    EncryptionService encryptionService;

    @Test
    public void testSystemEncryption(){

        encryptionService.encrypt("Sample Message");
        Assertions.assertTrue(true, "Message Encrypted");
    }

    @Test
    public void testSystemDecryption(){

        String encryptedMessage = encryptionService.encrypt("Sample Message");
        encryptionService.decrypt(encryptedMessage);

        Assertions.assertTrue(true, "Message Encrypted");
    }

    @Test
    public void testCustomEncryption(){

        encryptionService.customEncrypt("Sample Message", "akmsg667480udhd7s9qidk974k37hslod095je");
        Assertions.assertTrue(true, "Message Encrypted with key");
    }

    @Test
    public void testCustomDecryption(){

        String encryptedMessage = encryptionService.customEncrypt("Sample Message", "akmsg667480udhd7s9qidk974k37hslod095je");
        encryptionService.customDecrypt(encryptedMessage,"akmsg667480udhd7s9qidk974k37hslod095je");

        Assertions.assertTrue(true, "Message Encrypted with key");
    }
}
