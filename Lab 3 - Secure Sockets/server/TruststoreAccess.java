package examples.security;
import java.io.FileInputStream;
import java.security.cert.Certificate;
import java.security.KeyStore;
/** An example class that accesses a key within a keystore
 */
public class TruststoreAccess {
    static final String TRUSTSTORE = "LIUtruststore.ks";
    static final String CERTALIAS = "exampleRSAcert";
    static final String STOREPASSWD = "abcdef";
    /** The test method for the clas s
     */
    public static void main( String[] args) {
        try {
            KeyStore ts = KeyStore.getInstance( "JCEKS" );
            ts.load(new FileInputStream( TRUSTSTORE ),
                    STOREPASSWD.toCharArray() );
            Certificate c = ts.getCertificate( CERTALIAS );
            System.out.println( "The certificate type for "
                    + "alias " + CERTALIAS
                    + " is " + c.getType() );
        }
        catch ( Exception x ) {
            System.out.println( x );
            x.printStackTrace();
        }
    }
}
