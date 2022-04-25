import Connection.ClientSession;
import Connection.Session;

public class App {
    public static void main(String [] arg){
        Session clientSession = new ClientSession("localhost", 8800);
        if(!waitConnection(clientSession)){
            System.out.println("Failed to connect to the server.");
            return;
        }

    }
    private static boolean waitConnection(Session clientSession){
        return clientSession.reconnect(10);
    }
}
