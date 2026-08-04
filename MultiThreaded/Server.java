import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;

public class Server {

    static AtomicInteger totalHandled = new AtomicInteger(0);

    public Consumer<Socket> getConsumer() {
        return (clientSocket) -> {
            try (PrintWriter toSocket = new PrintWriter(clientSocket.getOutputStream(), true)) {
                toSocket.println("Hello from Server " + clientSocket.getRemoteSocketAddress());
                int count = totalHandled.incrementAndGet();
                System.out.println("Request #" + count + " handled -> " + clientSocket.getRemoteSocketAddress());
            } catch (IOException ex) {
                ex.printStackTrace();
            } finally {
                try {
                    clientSocket.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        };
    }

    public static void main(String[] args) throws Exception {
        int port = 8010;
        int backlog = 500;
        Server server = new Server();

        ServerSocket serverSocket = new ServerSocket(port, backlog);
        System.out.println("Server is running on port " + port + " (backlog=" + backlog + ")");

        while (true) {
            Socket clientSocket = serverSocket.accept();
            Thread thread = new Thread(() -> server.getConsumer().accept(clientSocket));
            thread.start();
        }
    }
}