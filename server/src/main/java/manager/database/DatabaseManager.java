package manager.database;

import content.Product;
import lombok.extern.log4j.Log4j2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@Log4j2
public class DatabaseManager {
    private final Connection connection;

    private PreparedStatement insertWithOwner;

    private PreparedStatement insertWithoutOwner;

    public DatabaseManager(String link, String user, String password) throws SQLException {
//        ssh -L <порт>:pg:5432 s<ISU>@se.ifmo.ru -p 2222
//        link = "localhost:<порт который используется в команде выше>/studs"
//        user = логин от хелиоса
//        password = пароль от хелиоса
        connection = DriverManager.getConnection("jdbc:postgresql://" + link, user, password);
        initPrepareStatement();
        log.info("Has been connected to database. ");
    }

    public PreparedStatement preparedStatement(String request) throws SQLException {
        return connection.prepareStatement(request);
    }

    private void initPrepareStatement() throws SQLException {
        insertWithOwner = preparedStatement("""
            WITH Person_id AS (
                INSERT INTO person (name,birthday, height, weight, passportid, creationdate)
                    VALUES (?, ?, ?, ?, ?, ?) RETURNING id
            ),Coordinates_id AS (
                INSERT INTO Coordinates (x,y,creationDate) VALUES (?,?,?) RETURNING id
            ),Product_id AS (
                INSERT INTO product (name, coordinates , creationdate, price, partnumber, cost, unit, owner)
                    VALUES (?, (SELECT id FROM Coordinates_id), ?, ?, ?, ?, (CAST(? AS unit)), (SELECT id FROM Person_id)) RETURNING id
            ) (SELECT id FROM Product_id);""");

        insertWithoutOwner = preparedStatement("""
            WITH Coordinates_id AS (
                INSERT INTO Coordinates (x,y,creationDate) VALUES (?,?,?) RETURNING id
            ),Product_id AS (
                INSERT INTO product (name, coordinates , creationdate, price, partnumber, cost, unit, owner)
                    VALUES (?, (SELECT id FROM Coordinates_id), ?, ?, ?, ?, (CAST(? AS unit)), null) RETURNING id
            ) (SELECT id FROM Product_id);""");
    }

    public void fillStatement(Product product) throws SQLException {
        if (product.getOwner() != null){
            insertWithOwner.setString(1, product.getOwner().getName());
            insertWithOwner.setString(2, product.getOwner().getBirthday().toString());
            insertWithOwner.setLong(3, product.getOwner().getHeight());
            insertWithOwner.setInt(4, product.getOwner().getWeight());
            insertWithOwner.setString(5, product.getOwner().getPassportID());
            insertWithOwner.setString(6, product.getCreationDate().toString());

            insertWithOwner.setInt(7, product.getCoordinates().getX());
            insertWithOwner.setInt(8, product.getCoordinates().getY());
            insertWithOwner.setString(9, product.getCreationDate().toString());

            insertWithOwner.setString(10, product.getName());
            insertWithOwner.setString(11, product.getCreationDate().toString());
            insertWithOwner.setDouble(12, product.getPrice());
            insertWithOwner.setString(13, product.getPartNumber());
            insertWithOwner.setDouble(14, product.getManufactureCost());
            insertWithOwner.setString(15, product.getUnitOfMeasure().getTitle().toUpperCase());
        } else {
            insertWithoutOwner.setInt(1, product.getCoordinates().getX());
            insertWithoutOwner.setInt(2, product.getCoordinates().getY());
            insertWithoutOwner.setString(3, product.getCreationDate().toString());

            insertWithoutOwner.setString(4, product.getName());
            insertWithoutOwner.setString(5, product.getCreationDate().toString());
            insertWithoutOwner.setDouble(6, product.getPrice());
            insertWithoutOwner.setString(7, product.getPartNumber());
            insertWithoutOwner.setDouble(8, product.getManufactureCost());
            insertWithoutOwner.setString(9, product.getUnitOfMeasure().getTitle().toUpperCase());
        }
    }

    public long executeInsert(PreparedStatement statement) throws SQLException {
        return statement.executeUpdate();
    }
}
