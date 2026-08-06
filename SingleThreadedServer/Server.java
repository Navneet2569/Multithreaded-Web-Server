import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.atomic.AtomicInteger;

public class Server {

    static AtomicInteger totalHandled = new AtomicInteger(0);

    public static void main(String[] args) throws Exception {
        int port = 8010;
        ServerSocket serverSocket = new ServerSocket(port);
        System.out.println("Server listening on port " + port);

        while (true) {
            Socket clientSocket = serverSocket.accept();

            try (PrintWriter toClient = new PrintWriter(clientSocket.getOutputStream(), true)) {
                toClient.println("Hello from Server " + clientSocket.getRemoteSocketAddress());
                int count = totalHandled.incrementAndGet();
                System.out.println("Request #" + count + " handled -> " + clientSocket.getRemoteSocketAddress());
            } catch (IOException ex) {
                ex.printStackTrace();
            } finally {
                clientSocket.close();
            }
        }
    }
}