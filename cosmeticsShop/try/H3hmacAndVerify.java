package client;

import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.Arrays;
import java.util.Base64;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class H3hmacAndVerify {
	
	String clientID = "";
	String clientSK ="";

   static Object readFromFile(String filename) throws Exception {
      FileInputStream fin = new FileInputStream(filename);
      ObjectInputStream oin = new ObjectInputStream(fin);
      Object object = oin.readObject();
      oin.close();
      return object;
   }

   public static void main(String[] args) throws Exception {

      // read secret key
      String encodedKey = (String) readFromFile("data/secretKey");
      byte[] decodedKey = Base64.getDecoder().decode(encodedKey);
      SecretKey sk = new SecretKeySpec(decodedKey, 0, decodedKey.length, "HmacSHA256");

      String encodedHmac = (String) readFromFile("data/hmac");
      byte[] sentHmac = Base64.getDecoder().decode(encodedHmac);
      String sendText = (String) readFromFile("data/sendText.txt");

      // calculate hmac
      Mac mac = Mac.getInstance("HmacSHA256");
      mac.init(sk);
      byte[] myHmac = mac.doFinal(sendText.getBytes());

      // check hmac
      System.out.println("Check: " + Arrays.equals(sentHmac, myHmac));
   }
}
