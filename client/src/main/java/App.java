import action.CommandData;
import actionClient.CommandHandlerClient;
import connection.ClientSession;
import connection.Session;
import exceptions.InvalidRecievedException;
import reader.ReaderConsole;
import transmissionClient.HandlerMesClient;

import java.io.IOException;
import java.util.List;

public class App {
    public static void main(String [] arg){
        Session clientSession = new ClientSession("localhost", 8800);
        ReaderConsole readerConsole;
        HandlerMesClient handlerMessage = new HandlerMesClient();
        CommandHandlerClient commandHandlerClient = new CommandHandlerClient();
        List<CommandData> commandData = commandHandlerClient.getCurrentCommandData();
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
        readerConsole = new ReaderConsole(commandData);



    }
    private static boolean waitConnection(Session clientSession){
        return clientSession.reconnect(10);
    }
}
