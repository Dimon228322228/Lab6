package connection;

import lombok.extern.log4j.Log4j2;
import manager.database.DatabaseManager;
import serverAction.CommandHandler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;

@Log4j2
public class AppServer {
    public static void main(String[] args){
        CommandHandler commandHandler = new CommandHandler();
        Server server;
        try {
            server = new Server(8800, commandHandler);
            System.out.println("Please, enter username for connect to database. ");
//            String user = new BufferedReader(new InputStreamReader(System.in)).readLine();
            String user = System.console().readLine();
            System.out.println("Please, enter password for connect to database. ");
//            String password = new BufferedReader(new InputStreamReader(System.in)).readLine();
            String password = String.valueOf(System.console().readPassword());
            DatabaseManager databaseManager = new DatabaseManager("localhost:9000/studs", user, password);
            commandHandler.setDatabaseManagerToExecutionResources(databaseManager);
            log.info("Server works. ");
            server.run();
            log.info("Server finished. ");
        } catch (IOException | SQLException e) {
            log.error(e.getMessage());
        }
    }
}
