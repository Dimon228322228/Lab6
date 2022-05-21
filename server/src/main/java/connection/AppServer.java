package connection;

import action.ResultAction;
import action.State;
import lombok.extern.log4j.Log4j2;
import manager.database.DatabaseManager;
import serverAction.CommandHandler;

import java.io.IOException;
import java.sql.SQLException;

@Log4j2
public class AppServer {
    public static void main(String[] args) throws ClassNotFoundException {
        Class.forName("org.postgresql.Driver");
        CommandHandler commandHandler = new CommandHandler();
        Server server;
        try {
            server = new Server(8800, commandHandler);
            DatabaseManager databaseManager = new DatabaseManager("localhost:9000/studs", "s336698", "");
            log.info("Server works. ");
            server.run();
            log.info("Server finished. ");
            ResultAction result = commandHandler.executeCommand("save");
            if(result.getState().equals(State.SUCCESS)) log.info(result.getDescription());
            else log.error(result.getDescription());
        } catch (IOException | SQLException e) {
            log.error(e.getMessage());
        }
    }
}
