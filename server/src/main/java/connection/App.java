package connection;

import serverAction.CommandHandler;

import javax.xml.bind.JAXBException;
import java.io.IOException;

public class App {
    public static void main(String[] args) {
        CommandHandler commandHandler;
        Server server;
        try {
            commandHandler = new CommandHandler();
        } catch (JAXBException e) {
            System.err.println(e.getMessage());
            return;
        }
        try {
            server = new Server(8800, commandHandler);
            server.run();
            System.out.println(commandHandler.executeCommand("save").getDescription());
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }

    }
}
