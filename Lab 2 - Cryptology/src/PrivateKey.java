import java.math.BigInteger;

public class PrivateKey {
    public final BigInteger d;
    public final BigInteger n;

    public PrivateKey(BigInteger dSent, BigInteger nSent) {
        this.d = dSent;
        this.n = nSent;
    }

    public String getKey() {
        return (this.d + ", " + this.n);
    }
}
