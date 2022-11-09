package messagingapp.ssd.Services;

import org.springframework.stereotype.Service;

import javax.crypto.Cipher;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Base64;
import java.util.Random;
import java.util.UUID;

import javax.crypto.spec.SecretKeySpec;

import static java.lang.Integer.parseInt;

@Service
public class EncryptionService {

    //public SystemConstants systemConstants;

    private static final String SYSTEM_ENCRYPTION_KEY = "2A462D4A614E635266556A586E327235";
    private static final int KEY_LENGTH = 111;
    private static final String CIPHER = "AES";

    private static SecretKeySpec secretKey;
    private static byte[] key;



    public void prepareSecreteKey(String myKey) {
        MessageDigest sha = null;
        try {
            key = myKey.getBytes(StandardCharsets.UTF_8);
            sha = MessageDigest.getInstance("SHA-1");
            key = sha.digest(key);
            key = Arrays.copyOf(key, 16);
            secretKey = new SecretKeySpec(key, CIPHER);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    public String encrypt(String strToEncrypt) {
        try {
            prepareSecreteKey(SYSTEM_ENCRYPTION_KEY);
            Cipher cipher = Cipher.getInstance(CIPHER);
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            return Base64.getEncoder().encodeToString(cipher.doFinal(strToEncrypt.getBytes("UTF-8")));
        } catch (Exception e) {
            System.out.println("Error while encrypting: " + e.toString());
        }
        return null;
    }

    public String customEncrypt(String strToEncrypt, String key) {
        try {
            prepareSecreteKey(key);
            Cipher cipher = Cipher.getInstance(CIPHER);
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            return Base64.getEncoder().encodeToString(cipher.doFinal(strToEncrypt.getBytes("UTF-8")));
        } catch (Exception e) {
            System.out.println("Error while encrypting: " + e.toString());
        }
        return null;
    }


    public String decrypt(String strToDecrypt) {
        try {
            prepareSecreteKey(SYSTEM_ENCRYPTION_KEY);
            Cipher cipher = Cipher.getInstance(CIPHER);
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            return new String(cipher.doFinal(Base64.getDecoder().decode(strToDecrypt)));
        } catch (Exception e) {
            System.out.println("Error while decrypting: " + e.toString());
        }
        return null;
    }

    public String customDecrypt(String strToDecrypt, String key) {
        try {
            prepareSecreteKey(key);
            Cipher cipher = Cipher.getInstance(CIPHER);
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            return new String(cipher.doFinal(Base64.getDecoder().decode(strToDecrypt)));
        } catch (Exception e) {
            System.out.println("Error while decrypting: " + e.toString());
        }
        return null;
    }


    public String getSecureRandomKey() {
        String key = UUID.randomUUID().toString();
        return key.replaceAll("-", "");
    }
}
