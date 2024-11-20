package com.alimurph.postcard.common.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.util.Base64;

@Service
public class EncryptionService {

    @Value("${application.security.card.secret-key}")
    private String encryptionKey;
    private final String encryptionCipher = "AES";
    private Key key;
    private Cipher cipher;

    public String encryptData(Object data) throws GeneralSecurityException, IOException {
        if(data == null)
            return null;

        initCipher(Cipher.ENCRYPT_MODE);

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
        objectOutputStream.writeObject(data);
        objectOutputStream.flush();

        return Base64.getEncoder().encodeToString(getCipher().doFinal(byteArrayOutputStream.toByteArray()));
    }

    public Object decryptData(String data) throws GeneralSecurityException, IOException, ClassNotFoundException {
        if(data == null || data == "")
            return null;

        initCipher(Cipher.DECRYPT_MODE);
        byte[] bytes = getCipher().doFinal(Base64.getDecoder().decode(data));
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
        ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);

        return objectInputStream.readObject();
    }

    private void initCipher(int opmode) throws GeneralSecurityException {
        getCipher().init(opmode, getKey());
    }

    private Cipher getCipher() throws GeneralSecurityException {
        if(cipher == null){
            cipher = Cipher.getInstance(encryptionCipher);
        }
        return cipher;
    }

    private Key getKey() {
        if(key == null){
            key = new SecretKeySpec(encryptionKey.getBytes(StandardCharsets.UTF_8), encryptionCipher);
        }
        return key;
    }
}
