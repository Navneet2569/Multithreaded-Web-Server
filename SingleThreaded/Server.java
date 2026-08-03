import java.io.BufferedReader;
import java.io.InputStreamReader;
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

            BufferedReader fromClient = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            PrintWriter toClient = new PrintWriter(clientSocket.getOutputStream(), true);
            String message;
            while ((message = fromClient.readLine()) != null) {
                System.out.println("Client says: " + message);
                String reply = respondTo(message);
                toClient.println(reply);
                if (message.equalsIgnoreCase("bye")) {
                    break;
                }
            }
            toClient.println("Hello from the Server!");

            clientSocket.close();
            System.out.println("Client disconnected. Waiting for next client...");
        }
    }

    private static String respondTo(String message) {
        String msg = message.trim().toLowerCase();

        if (msg.equals("hello") || msg.equals("hi") || msg.equals("hey")) {
            return "Hello there! How can I help you";
        } else if (msg.equals("time")) {
            return "Server time is: " + java.time.LocalDateTime.now().toString();
        } else if (msg.equals("bye")) {
            return "Goodbye! Have a great day!";
        } else {
            return "I don't understand: \"" + message + "\". Try 'hello', 'time', or 'bye'";
        }
    }
}