import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigInteger;

public class Main {
    public static void main(String[] args)
            throws IOException
    {
        // Enter data using BufferReader
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(System.in));

        System.out.println("Choose your secret prime numbers");
        System.out.print("p: ");
        int p = Integer.parseInt(reader.readLine());

        System.out.print("q: ");
        int q = Integer.parseInt(reader.readLine());

        // Calculate (p-1)(q-1)
        BigInteger phiN = (p.subtract(BigInteger.ONE)).multiply(q.subtract(BigInteger.ONE))
    }

    public BigInteger generateRandE(BigInteger phiN) {
        // Generate random number
        Random rand = new Random();
        BigInteger e = new BigInteger(phiN.bitLength(), rand);
    }
}