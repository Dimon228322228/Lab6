package manager.database;

import lombok.extern.log4j.Log4j2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Log4j2
public class DatabaseManager {
    private Connection connection;

    public DatabaseManager(String link, String user, String password) throws SQLException {
//        ssh -L <порт>:pg:5432 s<ISU>@se.ifmo.ru -p 2222
//        link = "localhost:<порт который используется в команде выше>"
//        user = логин от хелиоса
//        password = пароль от хелиоса
        connection = DriverManager.getConnection("jdbc:postgresql://" + link, user, password);
        log.info("Has been connected to database. ");
    }
}
