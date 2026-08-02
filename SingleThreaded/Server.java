import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main(String[] args) throws Exception {
        int port = 8010;
        ServerSocket serverSocket = new ServerSocket(port);
        System.out.println("Server listening on port " + port);
        while (true) {
            Socket clientSocket = serverSocket.accept();
            System.out.println("Connected: " + clientSocket.getRemoteSocketAddress());

            PrintWriter toClient = new PrintWriter(clientSocket.getOutputStream(), true);
            toClient.println("Hello from the Server!");

            clientSocket.close();
        }
    }
}