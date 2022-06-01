package connection;

import lombok.extern.log4j.Log4j2;
import manager.database.DatabaseManager;
import serverAction.CommandHandler;

import java.io.IOException;
import java.sql.SQLException;

@Log4j2
public class AppServer {
    public static void main(String[] args){
        CommandHandler commandHandler = new CommandHandler();
        Server server;
        try {
            server = new Server(8800, commandHandler);
            String user = System.getenv("User");
            String password = System.getenv("Pass");
            DatabaseManager databaseManager = new DatabaseManager("localhost:9000/studs", user, password);
            commandHandler.setDatabaseManagerToExecutionResources(databaseManager);
            commandHandler.synchronizeWithDatabase();
            log.info("Server works. ");
            server.run();
            log.info("Server finished. ");
        } catch (IOException | SQLException e) {
            log.error(e.getMessage());
        }
    }
}
