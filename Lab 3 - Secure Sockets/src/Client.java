import java.io.*;
import java.net.*;
import javax.net.ssl.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.*;

public class Client {

    private InetAddress host;
    private int port;
    // This is not a reserved port number
    static final int DEFAULT_PORT = 8189;
    static final String KEYSTORE = "client/LIUkeystore.ks";
    static final String TRUSTSTORE = "client/LIUtruststore.ks";
    static final String STOREPASSWD1 = "123456";
    static final String STOREPASSWD2 = "abcdef";
    static final String ALIASPASSWD = "123456";


    public Client( InetAddress host, int port ) {
        this.host = host;
        this.port = port;
    }
    public void run() {
        try {
            KeyStore ks = KeyStore.getInstance( "JCEKS" );
            ks.load(new FileInputStream(KEYSTORE), STOREPASSWD1.toCharArray());

            KeyStore ts = KeyStore.getInstance( "JCEKS" );
            ts.load(new FileInputStream(TRUSTSTORE), STOREPASSWD2.toCharArray());

            KeyManagerFactory kmf = KeyManagerFactory.getInstance("SunX509");
            kmf.init( ks, ALIASPASSWD.toCharArray() );

            TrustManagerFactory tmf = TrustManagerFactory.getInstance( "SunX509" );
            tmf.init(ts);

            SSLContext sslContext = SSLContext.getInstance( "TLS" );
            sslContext.init(kmf.getKeyManagers(), tmf.getTrustManagers(), null);
            SSLSocketFactory sslFact = sslContext.getSocketFactory();
            SSLSocket client =  (SSLSocket)sslFact.createSocket(host, port);
            client.setEnabledCipherSuites( client.getSupportedCipherSuites() );
            System.out.println("\n>>>> SSL/TLS handshake completed");

            BufferedReader socketFromServer;
            socketFromServer = new BufferedReader( new InputStreamReader( client.getInputStream() ) );
            PrintWriter socketToServer = new PrintWriter( client.getOutputStream(), true );

            boolean exit = false;

            while (!exit) {
                printMenu();
                String input = new BufferedReader(new InputStreamReader(System.in)).readLine();
                int option = Integer.parseInt(input);

                socketToServer.println(option);

                switch(option) {
                    case 1:
                        // Download file
                        System.out.println("Enter the file name: ");
                        try
                        {
                            String fileName = new BufferedReader(new InputStreamReader(System.in)).readLine();
                            System.out.println("Downloading the file from the server");
                            socketToServer.println(fileName);
                            String fileData = dataFromServer(socketFromServer);
                            createTextFile(fileName, fileData);
                        }
                        catch (Exception e)
                        {
                            System.out.println("There was an error when handling your request");
                        }
                        break;
                    case 2:
                        // Upload file
                        System.out.println("Please the name of the file that you want to upload:");
                        try {
                            String fileName = new BufferedReader(new InputStreamReader(System.in)).readLine();
                            Path filePath = Path.of("client/" + fileName);
                            String fileData = Files.readString(filePath);
                            System.out.println("Uploading file to the server");
                            socketToServer.println(fileName);
                            socketToServer.println(fileData);
                        }
                        catch (Exception e) {
                            System.out.println("There was an error when handling your request");
                            e.printStackTrace();
                        }
                        break;
                    case 3:
                        // Delete file
                        System.out.println("Please enter the name of the file you want to delete.");
                        try {
                            String fileName = new BufferedReader(new InputStreamReader(System.in)).readLine();
                            System.out.println("Deleting file from the server");
                            socketToServer.println(fileName);
                        }
                        catch (Exception e){
                            System.out.println("There was an error when handling your request");
                            e.printStackTrace();
                        }
                        break;
                    case 4:
                        System.out.println("Goodbye");
                        exit = true;
                        break;
                    default:
                        System.out.println("Wrong option, byebye");
                        exit = true;
                        break;
                }
            }
        }
        catch( Exception x ) {
            System.out.println( x );
            x.printStackTrace();
        }
    }

    private String dataFromServer(BufferedReader socketFromServer)
    {
        try {
            StringBuilder sb = new StringBuilder();
            String line = socketFromServer.readLine();
            String data="";
            while (line!= null) {
                sb.append(line);
                sb.append(System.lineSeparator());
                line = socketFromServer.readLine();
            }
            data = sb.toString();
            return data;
        }
        catch (Exception e)
        {
            System.out.println ("Error reading from server: " + e.toString());
            e.printStackTrace();
            return null;
        }
    }

    private void createTextFile(String fileName, String data)
    {
        String name = fileName;
        System.out.println(name);
        PrintWriter writer;
        try {
            writer = new PrintWriter(name);
            writer.print(data);
            writer.close();
            System.out.println("TEEEEST");
        }
        catch (Exception e)
        {
            System.out.println("Error writing to file: " + e.toString());
            e.printStackTrace();
        }

    }

    private String readFile(String fileName)
    {
        try {
            BufferedReader br = new BufferedReader(new FileReader(fileName));
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();

            while(line!=null) {
                sb.append(line);
                sb.append(System.lineSeparator());
                line = br.readLine();
            }

            return sb.toString();
        }
        catch (Exception e)
        {
            System.out.println("There was an error handling your request." + e.toString());
            e.printStackTrace();
            return null;
        }
    }

    public void printMenu() {
        System.out.println("");
        System.out.println("1. Download file from server?");
        System.out.println("2. Upload file to server?");
        System.out.println("3. Delete file from server?");
        System.out.println("4. Exit");
    }

    public static void main( String[] args ) {
        try {
            InetAddress host = InetAddress.getLocalHost();
            int port = DEFAULT_PORT;
            if ( args.length > 0 ) {
                port = Integer.parseInt( args[0] );
            }
            if ( args.length > 1 ) {
                host = InetAddress.getByName( args[1] );
            }
            Client client = new Client( host, port );
            client.run();
        }
        catch ( UnknownHostException uhx ) {
            System.out.println( uhx );
            uhx.printStackTrace();
        }
    }
}