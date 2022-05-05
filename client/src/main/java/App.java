import connection.ClientSession;
import connection.Session;
import exceptions.InvalidRecievedException;
import reader.ReaderConsole;
import transmissionClient.HandlerMesClient;

import java.io.IOException;

public class App {
    public static void main(String [] arg){
        Session clientSession = new ClientSession("localhost", 8800);
        ReaderConsole readerConsole;
        HandlerMesClient handlerMessage = new HandlerMesClient();
        if(!waitConnection(clientSession)){
            System.out.println("Failed to connect to the server.");
            return;
        }
        try {
            readerConsole = new ReaderConsole(handlerMessage.getCommandData(clientSession.getSocketChannel()));
        } catch (IOException | InvalidRecievedException e) {
            System.out.println("Can't get the list of commands from server.");
            return;
        }




    }
    private static boolean waitConnection(Session clientSession){
        return clientSession.reconnect(10);
    }
}
