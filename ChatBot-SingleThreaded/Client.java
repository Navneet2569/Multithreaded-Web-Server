import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) throws Exception {
        Socket socket = new Socket("localhost", 8010);

        BufferedReader fromServer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintWriter toServer = new PrintWriter(socket.getOutputStream(), true);
        Scanner keyboard = new Scanner(System.in);

        System.out.println("Connected to the server. Type messages (type 'bye' to quit):");

        while (true) {
            System.out.print("You: ");
            String userInput = keyboard.nextLine();

            toServer.println(userInput); // always send first, even "bye"

            String reply = fromServer.readLine();
            System.out.println("Server: " + reply);

            if (userInput.equalsIgnoreCase("bye")) {
                break; // now break AFTER sending + reading reply
            }
        }

        fromServer.close();
        toServer.close();
        keyboard.close();
        socket.close();
        System.out.println("Connection closed. Goodbye!");
    }
}