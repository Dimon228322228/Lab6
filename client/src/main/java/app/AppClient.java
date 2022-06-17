package app;

import action.ResultAction;
import action.State;
import actionClient.CommandHandler;
import connection.ClientSession;
import connection.Session;
import gui.SwingApp;
import transmissionClient.HandlerMesClient;

import javax.swing.*;


public class AppClient {
    public static void main(String [] arg){
        SwingUtilities.invokeLater(SwingApp::new);
        Session clientSession = new ClientSession("localhost", 8800);
        HandlerMesClient handlerMessage = new HandlerMesClient();
        CommandHandler commandHandler = new CommandHandler(handlerMessage, clientSession);
        ResultAction resultAction = commandHandler.login();
        if (resultAction.getState().equals(State.SUCCESS)) System.out.println(resultAction.getDescription());
        else System.err.println(resultAction.getDescription());
        commandHandler.run();
    }

}
