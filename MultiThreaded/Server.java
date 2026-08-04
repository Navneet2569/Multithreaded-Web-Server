package MultiThreaded;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.function.Consumer;

public class Server {

    public Consumer<Socket> getConsumer() {
        return (clientSocket) -> {
            try {
                PrintWriter toSocket = new PrintWriter(clientSocket.getOutputStream(), true);
                toSocket.println(" Hello from Server " + clientSocket.getRemoteSocketAddress());
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        };
    }

    public static void main(String[] args) throws Exception {
        int port = 8010;
        Server server = new Server();

        ServerSocket serverSocket = new ServerSocket(port);
        System.out.println("Server is running on port " + port);

        while (true) {
            Socket clientSocket = serverSocket.accept();

            Thread thread = new Thread(() -> server.getConsumer().accept(clientSocket));
            thread.start();
        }
    }
}
