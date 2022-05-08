import action.CommandData;
import actionClient.CommandController;
import actionClient.CommandHandler;
import connection.ClientSession;
import connection.Session;
import exceptions.InvalidRecievedException;
import transmissionClient.HandlerMesClient;

import java.io.IOException;
import java.util.List;

public class App {
    public static void main(String [] arg){
        Session clientSession = new ClientSession("localhost", 8800);
        HandlerMesClient handlerMessage = new HandlerMesClient();
        CommandController commandController = new CommandController();
        List<CommandData> commandData = commandController.getCurrentCommandData();
        if(!waitConnection(clientSession)){
            System.out.println("Failed to connect to the server.");
            return;
        }
        try {
            commandData.addAll(handlerMessage.getCommandData(clientSession.getSocketChannel()));
        } catch (IOException | InvalidRecievedException e) {
            System.out.println("Can't get the list of commands from server.");
            return;
        }
        CommandHandler commandHandler = new CommandHandler(commandController, handlerMessage, clientSession);
        commandHandler.run();
    }
    private static boolean waitConnection(Session clientSession){
        return clientSession.reconnect(10);
    }
}
