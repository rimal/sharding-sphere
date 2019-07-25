package com.example.shardingsphere.model.encryption;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.codec.Hex;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.security.Key;
import java.util.Arrays;

/**
 * @author Rimal
 */
@Converter
public class CryptoConverter implements AttributeConverter<String, String> {

  private static Logger logger = LoggerFactory.getLogger(CryptoConverter.class);

  private static final String ALGORITHM = "AES";
  private static Cipher encryptionCipher;
  private static Cipher decryptionCipher;
  private static volatile boolean initialized = false;

  public void initialize() {
    try {
      byte[] ENC_KEY = Arrays.copyOf("QQPfDncETQQDVXfH".getBytes(), 16);
      Key key = new SecretKeySpec(ENC_KEY, "AES");

      encryptionCipher = Cipher.getInstance(ALGORITHM);
      encryptionCipher.init(Cipher.ENCRYPT_MODE, key);

      decryptionCipher = Cipher.getInstance(ALGORITHM);
      decryptionCipher.init(Cipher.DECRYPT_MODE, key);
      initialized = true;
    } catch (Exception e) {
      logger.error("Exception during initialization of ciphers", e);
      throw new RuntimeException(e);
    }
  }

  @Override
  public String convertToDatabaseColumn(String plainText) {
    if (!initialized) {
      initialize();
    }
    if (plainText == null) {
      return null;
    }
    try {
      byte[] ciphertextBytes = encryptionCipher.doFinal(plainText.getBytes());
      String encrypted = new String(Hex.encode(ciphertextBytes));
      return encrypted;
    } catch (Exception e) {
      logger.error("Exception during encryption", e);
      throw new RuntimeException(e);
    }
  }

  @Override
  public String convertToEntityAttribute(String encryptedText) {
    if (!initialized) {
      initialize();
    }
    if (encryptedText == null) {
      return null;
    }
    try {
      byte[] decodeHex = Hex.decode(encryptedText);
      byte[] ciphertextBytes = decryptionCipher.doFinal(decodeHex);
      String decrypted = new String(ciphertextBytes);
      return decrypted;
    } catch (Exception e) {
      logger.error("Exception during decryption", e);
      throw new RuntimeException(e);
    }
  }
}
