package app;

import actionClient.CommandHandler;
import connection.ClientSession;
import connection.Session;
import gui.SwingApp;
import transmissionClient.HandlerMesClient;

import javax.swing.*;


public class AppClient {
    public static void main(String [] arg){
        Session clientSession = new ClientSession("localhost", 8800);
        HandlerMesClient handlerMessage = new HandlerMesClient();
        CommandHandler commandHandler = new CommandHandler(handlerMessage, clientSession);
        SwingUtilities.invokeLater(() -> {
            new SwingApp(commandHandler);
        });
        commandHandler.run();
    }

}
