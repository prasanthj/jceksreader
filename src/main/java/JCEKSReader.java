import java.io.FileInputStream;
import java.io.IOException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.util.Enumeration;

import javax.crypto.SecretKey;

/**
 * Created by prasanthj on 2019-11-04.
 */
public class JCEKSReader {

  // kubectl cp warehouse-1572892161-gfh4/metastore-0:/jceks/..data/secrets.jceks secrets.jceks
  public static void main(String[] args)
    throws KeyStoreException, IOException, CertificateException, NoSuchAlgorithmException, UnrecoverableKeyException {
    String fileName = args.length >= 1 ? args[0] : "/tmp/secrets.jceks";
    System.out.println("File: " + fileName);
    String passwd = args.length >= 2 ? args[1] : "none";
    System.out.println("Password to unlock: " + passwd);
    char[] password = passwd.toCharArray();
    KeyStore ks = KeyStore.getInstance("JCEKS");
    try (FileInputStream fis = new FileInputStream(fileName)) {
      ks.load(fis, password);
      Enumeration<String> enumeration = ks.aliases();
      while(enumeration.hasMoreElements()) {
        String alias = enumeration.nextElement();
        SecretKey secretKey = (SecretKey) ks.getKey(alias, password);
        System.out.println(alias + ": " + new String(secretKey.getEncoded()));
      }
    }
  }
}
