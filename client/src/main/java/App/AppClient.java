package App;

import actionClient.CommandHandler;
import connection.ClientSession;
import connection.Session;
import exceptions.InvalidRecievedException;
import transmissionClient.HandlerMesClient;

import java.io.IOException;

public class AppClient {
    public static void main(String [] arg){
        Session clientSession = new ClientSession("localhost", 8800);
        HandlerMesClient handlerMessage = new HandlerMesClient();
        CommandHandler commandHandler = new CommandHandler(handlerMessage, clientSession);
        if(!waitConnection(clientSession)){
            System.out.println("Failed to connect to the server.");
            return;
        }
        try {
            commandHandler.updateCommandData();
        } catch (IOException | InvalidRecievedException | NullPointerException e) {
            System.out.println("Can't get the list of commands from server.");
            return;
        }
        commandHandler.run();
    }
    private static boolean waitConnection(Session clientSession){
        return clientSession.reconnect(10);
    }
}
