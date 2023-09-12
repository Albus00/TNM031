import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.util.Random;

public class Main {
    public static void main(String[] args)
            throws IOException
    {
        // Enter data using BufferReader
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(System.in));

        System.out.println("Choose your secret prime numbers");
        System.out.print("p: ");
        BigInteger p = new BigInteger(reader.readLine());

        System.out.print("q: ");
        BigInteger q = new BigInteger(reader.readLine());
    }
}