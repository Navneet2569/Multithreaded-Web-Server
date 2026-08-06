import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

public class Server {

    public Consumer<Socket> getConsumer() {
        return (clientSocket) -> {
            try (PrintWriter toClient = new PrintWriter(clientSocket.getOutputStream(), true)) {
                toClient.println("Hello from Server " + clientSocket.getRemoteSocketAddress());
                System.out.println("Handled request from " + clientSocket.getRemoteSocketAddress());
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
        int poolSize = 100;

        Server server = new Server();
        ExecutorService pool = Executors.newFixedThreadPool(poolSize);

        ServerSocket serverSocket = new ServerSocket(port);
        System.out.println("Server is running on port " + port + " (pool size=" + poolSize + ")");

        while (true) {
            Socket clientSocket = serverSocket.accept();
            pool.execute(() -> server.getConsumer().accept(clientSocket));
        }
    }
}