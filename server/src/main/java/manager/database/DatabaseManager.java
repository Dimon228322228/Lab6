package manager.database;

import content.Product;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.RandomStringUtils;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;

@Log4j2
public class DatabaseManager {
    private Connection connection;
    String link;
    String user;
    String password;

    private PreparedStatement insert_with_owner;

    private PreparedStatement insert_without_owner;

    private PreparedStatement insert_login_data;

    private PreparedStatement select_salt_by_login;

    private PreparedStatement select_password_by_login;

    @Getter private final String paper = "fj#fOW2T>b";


    public DatabaseManager(String link, String user, String password) throws SQLException {
//        ssh -L <порт>:pg:5432 s<ISU>@se.ifmo.ru -p 2222
//        link = "localhost:<порт который используется в команде выше>/studs"
//        user = логин от хелиоса
//        password = пароль от хелиоса
        this.link = link;
        this.user = user;
        this.password = password;
        bindToDatabase();
        initPrepareStatement();
        log.info("Has been connected to database. ");
    }

    public void bindToDatabase() throws SQLException {
        if (connection == null || connection.isClosed())
            connection = DriverManager.getConnection("jdbc:postgresql://" + link, user, password);
    }

    public PreparedStatement preparedStatement(String request) throws SQLException {
        return connection.prepareStatement(request);
    }

    private void initPrepareStatement() throws SQLException {
        insert_with_owner = preparedStatement("""
            WITH Person_id AS (
                INSERT INTO person (name,birthday, height, weight, passportid, creationdate)
                    VALUES (?, ?, ?, ?, ?, ?) RETURNING id
            ),Coordinates_id AS (
                INSERT INTO Coordinates (x,y,creationDate) VALUES (?,?,?) RETURNING id
            ),Product_id AS (
                INSERT INTO product (name, coordinates , creationdate, price, partnumber, cost, unit, owner, username)
                    VALUES (?, (SELECT id FROM Coordinates_id), ?, ?, ?, ?, (CAST(? AS unit)), (SELECT id FROM Person_id), ?) RETURNING id
            ) (SELECT id FROM Product_id);""");

        insert_without_owner = preparedStatement("""
            WITH Coordinates_id AS (
                INSERT INTO Coordinates (x,y,creationDate) VALUES (?,?,?) RETURNING id
            ),Product_id AS (
                INSERT INTO product (name, coordinates , creationdate, price, partnumber, cost, unit, owner, username)
                    VALUES (?, (SELECT id FROM Coordinates_id), ?, ?, ?, ?, (CAST(? AS unit)), null, ?) RETURNING id
            ) (SELECT id FROM Product_id);""");

        insert_login_data = preparedStatement("""
                INSERT INTO access (login,password,salt) VALUES (?,?,?)""");

        select_salt_by_login = preparedStatement("select salt from access where login = ?");
        select_password_by_login = preparedStatement("select password from access where login = ?");
    }

    private boolean fillStatementInsert(Product product) throws SQLException {
        if (product.getOwner() != null){
            insert_with_owner.setString(1, product.getOwner().getName());
            insert_with_owner.setString(2, product.getOwner().getBirthday().toString());
            insert_with_owner.setLong(3, product.getOwner().getHeight());
            insert_with_owner.setInt(4, product.getOwner().getWeight());
            insert_with_owner.setString(5, product.getOwner().getPassportID());
            insert_with_owner.setString(6, product.getCreationDate().toString());

            insert_with_owner.setInt(7, product.getCoordinates().getX());
            insert_with_owner.setInt(8, product.getCoordinates().getY());
            insert_with_owner.setString(9, product.getCreationDate().toString());

            insert_with_owner.setString(10, product.getName());
            insert_with_owner.setString(11, product.getCreationDate().toString());
            insert_with_owner.setDouble(12, product.getPrice());
            insert_with_owner.setString(13, product.getPartNumber());
            insert_with_owner.setDouble(14, product.getManufactureCost());
            if (product.getUnitOfMeasure() != null) insert_with_owner.setString(15, product.getUnitOfMeasure().getTitle().toUpperCase());
            else insert_with_owner.setString(15, null);
            insert_with_owner.setString(16, product.getUsername());
            return true;
        } else {
            insert_without_owner.setInt(1, product.getCoordinates().getX());
            insert_without_owner.setInt(2, product.getCoordinates().getY());
            insert_without_owner.setString(3, product.getCreationDate().toString());

            insert_without_owner.setString(4, product.getName());
            insert_without_owner.setString(5, product.getCreationDate().toString());
            insert_without_owner.setDouble(6, product.getPrice());
            insert_without_owner.setString(7, product.getPartNumber());
            insert_without_owner.setDouble(8, product.getManufactureCost());
            if (product.getUnitOfMeasure() != null) insert_without_owner.setString(9, product.getUnitOfMeasure().getTitle().toUpperCase());
            else insert_without_owner.setString(9, null);
            insert_without_owner.setString(10, product.getUsername());
            return false;
        }
    }

    public long executeInsert(Product product) throws SQLException {
        ResultSet result;
        if (fillStatementInsert(product)) result = insert_with_owner.executeQuery();
        else result = insert_without_owner.executeQuery();
        result.next();
        return result.getInt("id");
        // write next() behind invoke getint()
    }

    private void fillInsertLoginData(String login, String password) throws SQLException{
        String salt = RandomStringUtils.random(10, true, true);
        insert_login_data.setString(1, login);
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            insert_login_data.setString(2, new String(md5.digest((paper + password + salt).getBytes(StandardCharsets.UTF_8)), StandardCharsets.UTF_8));
        } catch (NoSuchAlgorithmException ignore) {}
        insert_login_data.setString(3, salt);
    }

    public void executeInsertAccessData(String login, String password) throws SQLException {
        fillInsertLoginData(login, password);
        insert_login_data.executeUpdate();
    }

    private void fillSelectSalt(String login) throws SQLException {
        select_salt_by_login.setString(1, login);
    }

    private void fillSelectPassword(String login) throws SQLException {
        select_password_by_login.setString(1, login);
    }

    public String executeSelectSalt(String login) throws SQLException {
        fillSelectSalt(login);
        ResultSet answer = select_salt_by_login.executeQuery();
        answer.next();
        return answer.getString("salt");
    }

    public String executeSelectPassword(String login) throws SQLException {
        fillSelectPassword(login);
        ResultSet answer = select_password_by_login.executeQuery();
        answer.next();
        return answer.getString("password");
    }
}
