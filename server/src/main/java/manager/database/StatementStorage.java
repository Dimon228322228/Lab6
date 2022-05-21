package manager.database;

import lombok.Getter;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class StatementStorage {
    @Getter private PreparedStatement insertStatement;
    @Getter private PreparedStatement loadStatement;
    @Getter private PreparedStatement createProductTable;

    private DatabaseManager databaseManager;

    public StatementStorage(DatabaseManager databaseManager){
        this.databaseManager = databaseManager;
    }

    private void setInsertStatement() throws SQLException {
        insertStatement = databaseManager.preparedStatement("INSERT INTO Products(" +
                                            "Id, Name, X, Y, CreationDate, Price, PartNumber, Cost, Unit, OwnerId) " +
                                            "VALUES(?,?,?,?,?,?,?,?,?,?)");
    }

    private void setLoadStatement() throws SQLException {
        loadStatement = databaseManager.preparedStatement("SELECT * FROM Products");
    }

    private void setCreateProductTable() throws SQLException {
        createProductTable = databaseManager.preparedStatement(
                "CREATE TABLE IF NOT EXISTS Products(" +
                            "Id INTEGER PRIMARY KEY," +
                            "Name VARCHAR(100) NOT NULL CHECK (Name <> '')," +
                            "X INTEGER NOT NULL CHECK(X <= 938) ," +
                            "Y INTEGER NOT NULL ," +
                            "CreationDate SMALLDATETIME NOT NULL," +
                            "Price FLOAT NOT NULL CHECK(Price > 0)," +
                            "PartNumber TEXT NULL CHECK(PartNumber >= 22)," +
                            "Cost FLOAT NOT NULL ," +
                            "Unit VARCHAR(20) NULL CHECK (Unit = 'KILOGRAMS' OR Unit = 'CENTIMETERS' OR Unit = 'PCS' OR Unit = 'MILLILITERS' OR Unit = 'GRAMS') ," +
                            "OwnerId INTEGER NULL" +
                        ");");
    }

}
