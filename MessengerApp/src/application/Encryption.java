package application;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

/*
 * Baramey: This has been taken off from StackOverflow
 * http://www.java2s.com/Code/Java/Security/EncryptingaStringwithDES.htm
 * https://stackoverflow.com/questions/23561104/how-to-encrypt-and-decrypt-string-with-my-passphrase-in-java-pc-not-mobile-plat
 */


import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;


/**
 *
 */
public class Encryption {

  Cipher ecipher;
  Cipher dcipher;

  Encryption(SecretKey key) throws Exception {
    ecipher = Cipher.getInstance("AES");
    dcipher = Cipher.getInstance("AES");
    ecipher.init(Cipher.ENCRYPT_MODE, key);
    dcipher.init(Cipher.DECRYPT_MODE, key);
  }

  public String encrypt(String str) throws Exception {
    // Encode the string into bytes using utf-8
    byte[] utf8 = str.getBytes("UTF8");

    // Encrypt
    byte[] enc = ecipher.doFinal(utf8);

    // Encode bytes to base64 to get a string
    return new String(Base64.getMimeEncoder().encode(enc),
            StandardCharsets.UTF_8);
    //return new sun.misc.BASE64Encoder().encode(enc);
  }

  public String decrypt(String str) throws Exception {
    // Decode base64 to get bytes
	byte[] dec = Base64.getMimeDecoder().decode(str);
		
    //byte[] dec = new sun.misc.BASE64Decoder().decodeBuffer(str);
    

    byte[] utf8 = dcipher.doFinal(dec);

    // Decode using utf-8
    return new String(utf8, "UTF8");
//	return new String (dec);
  }


    public static void main(String args []) throws Exception
    {

        String data = "Don't tell anybody!";
        String password = "Bar12345Bar12345";

        //SecretKey key = KeyGenerator.getInstance("AES").generateKey();
        SecretKey key = new SecretKeySpec(password.getBytes(), "AES");
        Encryption encrypter = new Encryption(key);

        System.out.println("Original String: " + data);

        String encrypted = encrypter.encrypt(data);

        System.out.println("Encrypted String: " + encrypted);

        String decrypted = encrypter.decrypt(encrypted);

        System.out.println("Decrypted String: " + decrypted);
    }
}
