import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.concurrent.atomic.AtomicInteger;

public class Client {

    static AtomicInteger successCount = new AtomicInteger(0);
    static AtomicInteger failureCount = new AtomicInteger(0);

    public Runnable getRunnable() {
        return () -> {
            int port = 8010;
            Socket socket = null;
            try {
                socket = new Socket("localhost", port);
                try (BufferedReader fromSocket = new BufferedReader(
                        new InputStreamReader(socket.getInputStream()))) {

                    String line = fromSocket.readLine();
                    System.out.println("Response from Server: " + line);
                    successCount.incrementAndGet();
                }
            } catch (IOException ex) {
                failureCount.incrementAndGet();
            } finally {
                if (socket != null) {
                    try {
                        socket.close();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        };
    }

    public static void main(String[] args) throws InterruptedException {
        Client client = new Client();
        int totalRequests = 100;
        Thread[] threads = new Thread[totalRequests];

        long start = System.nanoTime();

        for (int i = 0; i < totalRequests; i++) {
            threads[i] = new Thread(client.getRunnable());
            threads[i].start();
            Thread.sleep(1);
        }

        for (Thread t : threads) {
            t.join();
        }

        long elapsedMillis = (System.nanoTime() - start) / 1_000_000;

        System.out.println("---- Results ----");
        System.out.println("Total requests: " + totalRequests);
        System.out.println("Successes: " + successCount.get());
        System.out.println("Failures: " + failureCount.get());
        System.out.println("Time taken: " + elapsedMillis + " ms");
        System.out.println("Requests/sec: " + (totalRequests * 1000.0 / elapsedMillis));
    }
}