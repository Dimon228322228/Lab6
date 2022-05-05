package connection;

import manager.CollectionManager;
import manager.QueueManager;
import serverAction.commandHandler.CommandHandler;
import serverAction.commandHandler.CommandHandlerImpl;

import javax.xml.bind.JAXBException;
import java.io.IOException;

public class App {
    public static void main(String[] args) {
        CollectionManager collectionManager;
        CommandHandler commandHandler;
        Server server;
        try {
            collectionManager = QueueManager.getInstance();
            commandHandler = new CommandHandlerImpl();
        } catch (JAXBException e) {
            System.err.println(e.getMessage());
            return;
        }
        try {
            server = new Server(8800, collectionManager, commandHandler);
            server.run();
            collectionManager.save();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }

    }
}
