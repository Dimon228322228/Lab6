package Connection;

import Action.CommandManager;
import Manager.CollectionManager;
import Manager.QueueManager;
import ServerAction.CommandManag;

import javax.xml.bind.JAXBException;
import java.io.IOException;

public class App {
    public static void main(String[] args) {
        CollectionManager collectionManager;
        CommandManager commandManager;
        Server server;
        try {
            collectionManager = QueueManager.getInstance();
            commandManager = new CommandManag();
        } catch (JAXBException e) {
            System.err.println(e.getMessage());
            return;
        }
        try {
            server = new Server(8800, collectionManager, commandManager);
            server.run();
            collectionManager.save();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }

    }
}
