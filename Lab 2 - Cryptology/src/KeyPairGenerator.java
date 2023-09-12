import java.math.BigInteger;
import java.util.Random;

public class KeyPairGenerator {
    private final BigInteger p;
    private final BigInteger q;
    private final BigInteger phiN;
    private final BigInteger e;
    private final PublicKey pub;
    private final PrivateKey priv;

    public KeyPairGenerator(BigInteger p, BigInteger q) {
        // Calculate (p-1)(q-1)
        phiN = (p.subtract(BigInteger.ONE)).multiply(q.subtract(BigInteger.ONE));

        // Generate e
        e = generateRandE(phiN);

        pub = new PublicKey(e, p.multiply(q));
        priv = new PrivateKey(d, p.multiply(q));
    }

    private static BigInteger generateRandE(BigInteger phiN) {
        // Generate random number
        Random rand = new Random();
        BigInteger e;

        // Generate a number that is greater than 1, less than phiN and GDC(e, phiN) is equal to 1.
        do {
            e = new BigInteger(phiN.bitLength(), rand);
        } while (e.compareTo(BigInteger.ONE) <= 0 || e.compareTo(phiN) >= 0 || !e.gcd(phiN).equals(BigInteger.ONE));

        // When conditions are met - return e
        return e;
    }

    public PublicKey getPublic() {
        return pub;
    }
    public PrivateKey getPrivate() {
        return priv;
    }
}
