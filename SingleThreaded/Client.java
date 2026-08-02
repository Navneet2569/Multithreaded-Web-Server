import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Socket;

public class Client {
    public static void main(String args[]) throws Exception {
        Socket socket = new Socket("localhost", 8010);
        BufferedReader fromServer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        System.out.println("Server says: " + fromServer.readLine());
        socket.close();
    }
}
