import java.io.BufferedReader;
import java.io.FileReader;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Scanner;
import java.util.stream.Stream;

public class Main {

    public static void main(String[] args) {
        String url;
        InetAddress[] addrs;
        try {
            BufferedReader reader = new BufferedReader(new FileReader("ips.txt"));
            String host;
            Stream.Builder<String> builder = Stream.builder();
            url = reader.readLine();
            while ((host = reader.readLine()) != null) {
                host = host.trim();
                if (host.isEmpty()) continue;
                builder.accept(host);
            }
            addrs = builder.build().distinct()
                    .map(h -> {
                        try {
                            return InetAddress.getByName(h);
                        } catch (UnknownHostException e) {
                            e.printStackTrace();
                            return null;
                        }
                    }).toArray(InetAddress[]::new);
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
        Scanner scanner = new Scanner(System.in);
        System.out.print("Times: ");
        Tester tester = new Tester(url, addrs, scanner.nextInt());
        System.out.println(url);
        tester.test();
    }
}
