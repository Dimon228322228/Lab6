package app;

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
        if(!waitConnection(clientSession)) {
            System.out.println("Failed connect to the server. ");
        } else {
            System.out.println("Connected to the server. ");
            try {
                commandHandler.updateCommandData();
            } catch (IOException | InvalidRecievedException | NullPointerException e) {
                System.out.println("Can't get the list of commands from server.");
                disconnect(clientSession);
            }
        }
        commandHandler.run();
    }

    private static boolean waitConnection(Session clientSession){
        return clientSession.reconnect(3);
    }

    private static void disconnect(Session session){
        try {
            session.disconnect();
        } catch (IOException ignored) {}
    }
}
