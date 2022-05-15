package connection;

import action.ResultAction;
import action.State;
import lombok.extern.log4j.Log4j2;
import serverAction.CommandHandler;

import java.io.IOException;

@Log4j2
public class AppServer {
    public static void main(String[] args) {
        CommandHandler commandHandler = new CommandHandler();
        Server server;
        try {
            server = new Server(8800, commandHandler);
            log.info("Server works. ");
            server.run();
            log.info("Server finished. ");
            ResultAction result = commandHandler.executeCommand("save");
            if(result.getState().equals(State.SUCCESS)) log.info(result.getDescription());
            else log.error(result.getDescription());
        } catch (IOException e) {
            log.error(e.getMessage());
        }

    }
}
