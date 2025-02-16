package com.guna.yumzoom.security;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

public class EncryptionUtil {

    private static final String SECRET_KEY = "1234567890123456";
    // Encrypt the restaurant ID
    public static String encrypt(String data) throws Exception {
        Cipher cipher = Cipher.getInstance("AES");
        SecretKeySpec keySpec = new SecretKeySpec(SECRET_KEY.getBytes(), "AES");
        cipher.init(Cipher.ENCRYPT_MODE, keySpec);
        byte[] encryptedBytes = cipher.doFinal(data.getBytes());
        return Base64.getUrlEncoder().encodeToString(encryptedBytes);
    }

    // Decrypt the restaurant ID
    public static String decrypt(String encryptedData) throws Exception {
        Cipher cipher = Cipher.getInstance("AES");
        SecretKeySpec keySpec = new SecretKeySpec(SECRET_KEY.getBytes(), "AES");
        cipher.init(Cipher.DECRYPT_MODE, keySpec);
        byte[] decryptedBytes = cipher.doFinal(Base64.getUrlDecoder().decode(encryptedData));
        return new String(decryptedBytes);
    }
}
