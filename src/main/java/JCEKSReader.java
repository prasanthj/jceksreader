import java.io.FileInputStream;
import java.io.IOException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;

import javax.crypto.SecretKey;

/**
 * Created by prasanthj on 2019-11-04.
 */
public class JCEKSReader {

  // kubectl cp warehouse-1572892161-gfh4/metastore-0:/jceks/..data/secrets.jceks secrets.jceks
  public static void main(String[] args)
    throws KeyStoreException, IOException, CertificateException, NoSuchAlgorithmException, UnrecoverableKeyException {
    String fileName = args.length == 1 ? args[0] : "/tmp/secrets.jceks";
    char[] password = "none".toCharArray();
    String alias = "javax.jdo.option.connectionpassword";
    KeyStore ks = KeyStore.getInstance("JCEKS");
    try (FileInputStream fis = new FileInputStream(fileName)) {
      ks.load(fis, password);
      SecretKey secretKey = (SecretKey) ks.getKey(alias, password);
      System.out.println(new String(secretKey.getEncoded()));
    }
  }
}
