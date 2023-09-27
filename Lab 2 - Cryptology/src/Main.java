import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigInteger;


public class Main {
    private static KeyPairGenerator keyGen;
    private static PrivateKey priv;
    public static PublicKey pub;

    public static void main(String[] args)
            throws IOException
    {
        // Enter data using BufferReader
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(System.in));

        //System.out.println("Choose your secret prime numbers");
        //System.out.print("p: ");
        //BigInteger p = new BigInteger(reader.readLine());
        BigInteger p = new BigInteger("885320963");

        //System.out.print("q: ");
        //BigInteger q = new BigInteger(reader.readLine());
        BigInteger q = new BigInteger("238855417");

        keyGen = new KeyPairGenerator(p, q);
        priv = keyGen.getPrivate();
        pub = keyGen.getPublic();

        System.out.println("Private: " + priv.getKey());
        System.out.println("Public: " + pub.getKey());
        System.out.println("Bob sends his public key to YOU");
        System.out.println("...");

        System.out.print("Input message: ");
        String msg = reader.readLine();

        MessageGenerator gen = new MessageGenerator();
        BigInteger genMsg = new BigInteger(gen.generateMsg(msg));
        System.out.println("Message: " + genMsg);

        // Encrypt the message
        BigInteger c = genMsg.modPow(pub.e, pub.n);
        System.out.println("You send c = " + c + " to Bob");
        System.out.println("...");

        // Decrypt the message
        System.out.println("Bob decrypts the message");
        BigInteger d = pub.e.modInverse(keyGen.phiN);
        System.out.println("d = " + d);
        BigInteger msgRec = c.modPow(d, pub.n);
        String msgToTranslate = msgRec.toString();
        System.out.println("Message before translation: " + msgToTranslate);
        String message = gen.decryptMsg(msgToTranslate);
        System.out.println("Recovered message: " + message);

        /*
      import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigInteger;


public class Main {
    private static KeyPairGenerator keyGen;
    private static PrivateKey priv;
    public static PublicKey pub;

    public static void main(String[] args)
            throws IOException
    {
        // Enter data using BufferReader
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(System.in));

        //System.out.println("Choose your secret prime numbers");
        //System.out.print("p: ");
        //BigInteger p = new BigInteger(reader.readLine());
        BigInteger p = new BigInteger("885320963");

        //System.out.print("q: ");
        //BigInteger q = new BigInteger(reader.readLine());
        BigInteger q = new BigInteger("238855417");

        keyGen = new KeyPairGenerator(p, q);
        priv = keyGen.getPrivate();
        pub = keyGen.getPublic();

        System.out.println("Private: " + priv.getKey());
        System.out.println("Public: " + pub.getKey());
        System.out.println("Bob sends his public key to YOU");
        System.out.println("...");

        System.out.print("Input message: ");
        String msg = reader.readLine();

        MessageGenerator gen = new MessageGenerator();
        String genMsg = gen.generateMsg(msg);
        System.out.println("Message: " + genMsg);
        String[] genMsgSplit = genMsg.split(" ");

        // Encrypt the message
        String c = "";
        for (String s: genMsgSplit) {
            System.out.println(s);
            BigInteger msgSegment = new BigInteger(s);
            BigInteger msgSegmentEncrypt = msgSegment.modPow(pub.e, pub.n);
            System.out.println(msgSegmentEncrypt);
            c += msgSegmentEncrypt.toString() + " ";
        }

        System.out.println("You send c = " + c + " to Bob");
        System.out.println("...");

        // Decrypt the message
        System.out.println("Bob decrypts the message");
        BigInteger d = pub.e.modInverse(keyGen.phiN);
        System.out.println("d = " + d);

        String[] genMsgSplit2 = c.split(" ");
        String msgToTranslate = "";
        for (String s: genMsgSplit2) {
            System.out.println(s);
            BigInteger msgSegment = new BigInteger(s);
            BigInteger msgSegmentDecrypt = msgSegment.modPow(d, pub.n);
            msgToTranslate += msgSegmentDecrypt.toString();
            System.out.println(msgSegmentDecrypt.toString());
        }
        System.out.println("Message before translation: " + msgToTranslate);
        String message = gen.decryptMsg(msgToTranslate);
        System.out.println("Recovered message: " + message);

    }
}
        * */
    }
}