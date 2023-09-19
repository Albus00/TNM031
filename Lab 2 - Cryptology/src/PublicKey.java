import java.math.BigInteger;

public class PublicKey {
    public final BigInteger e;
    public final BigInteger n;

    public PublicKey(BigInteger eSent, BigInteger nSent) {
        this.e = eSent;
        this.n = nSent;
    }

    public String getKey() {
        return (this.e + ", " + this.n);
    }
}
