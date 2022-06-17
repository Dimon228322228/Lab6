package manager.database;

import content.Product;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.RandomStringUtils;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;

public class StatementStorage {
    @Setter private Connection connection;

    @Getter private PreparedStatement insert_with_owner;

    @Getter private PreparedStatement insert_without_owner;

    @Getter private PreparedStatement insert_login_data;

    @Getter private PreparedStatement select_salt_by_login;

    @Getter private PreparedStatement select_password_by_login;

    @Getter private PreparedStatement deleted_by_id;

    @Getter private PreparedStatement update_by_id_with_owner;

    @Getter private PreparedStatement update_by_id_without_owner;

    @Getter private PreparedStatement clear_collection_by_username;

    @Getter private PreparedStatement get_all;

    @Getter private PreparedStatement get_person_by_id;

    @Getter private final String update_dependent_table = """
            delete from person where not exists(select owner from product where product.owner = person.id);
            delete from coordinates where not exists(select product.coordinates from product where product.coordinates = coordinates.id)""";

    public StatementStorage(Connection connection){
        this.connection = connection;
    }

    public void initPrepareStatement() throws SQLException {
        insert_with_owner = preparedStatement("""
            WITH Person_id AS (
                INSERT INTO person (name,birthday, height, weight, passportid)
                    VALUES (?, ?, ?, ?, ?) RETURNING id
            ),Coordinates_id AS (
                INSERT INTO Coordinates (x,y) VALUES (?,?) RETURNING id
            ),Product_id AS (
                INSERT INTO product (name, coordinates , creationdate, price, partnumber, cost, unit, owner, username)
                    VALUES (?, (SELECT id FROM Coordinates_id), ?, ?, ?, ?, (CAST(? AS unit)), (SELECT id FROM Person_id), ?) RETURNING id
            ) (SELECT id FROM Product_id);""");

        insert_without_owner = preparedStatement("""
            WITH Coordinates_id AS (
                INSERT INTO Coordinates (x,y) VALUES (?,?) RETURNING id
            ),Product_id AS (
                INSERT INTO product (name, coordinates , creationdate, price, partnumber, cost, unit, owner, username)
                    VALUES (?, (SELECT id FROM Coordinates_id), ?, ?, ?, ?, (CAST(? AS unit)), null, ?) RETURNING id
            ) (SELECT id FROM Product_id);""");

        insert_login_data = preparedStatement("""
                INSERT INTO access (login,password,salt) VALUES (?,?,?)""");

        select_salt_by_login = preparedStatement("select salt from access where login = ?");
        select_password_by_login = preparedStatement("select password from access where login = ?");

        deleted_by_id = preparedStatement("DELETE FROM product WHERE id = ?");

        update_by_id_with_owner = preparedStatement("""
                with Person_id as (insert into person (name, birthday, height, weight, passportid) values (?, ?, ?, ?, ?) returning id),
                    Coordinate_id as (insert into coordinates (x, y) values (?, ?) returning id)
                update product set name = ?,coordinates = (Select id from Coordinate_id), creationdate = ?, price = ?, partnumber = ?, cost = ?,
                        unit = (CAST(? AS unit)), owner = (Select id from Person_id) where id = ?""");

        update_by_id_without_owner = preparedStatement("""
                with Coordinate_id as (insert into coordinates (x, y) values (?, ?) returning id)
                update product set name = ?, coordinates = (Select id from Coordinate_id), creationdate = ?, price = ?,
                        partnumber = ?, cost = ?, unit = (CAST(? AS unit)), owner = null where id = ?""");

        clear_collection_by_username = preparedStatement("DELETE FROM product WHERE username = ?");

        get_all = preparedStatement("select * from product, coordinates where product.coordinates = coordinates.id");

        get_person_by_id = preparedStatement("select * from person where id = ?");
    }

    private PreparedStatement preparedStatement(String request) throws SQLException {
        return connection.prepareStatement(request);
    }

    private void fillProductWithoutOwner(PreparedStatement ps, Product product) throws SQLException {
        ps.setInt(1, product.getCoordinates().getX());
        ps.setInt(2, product.getCoordinates().getY());

        ps.setString(3, product.getName());
        ps.setString(4, new SimpleDateFormat("dd.MM.yyyy HH:mm:ss").format(product.getCreationDate()));
        ps.setDouble(5, product.getPrice());
        ps.setString(6, product.getPartNumber());
        ps.setDouble(7, product.getManufactureCost());
        if (product.getUnitOfMeasure() != null) ps.setString(8, product.getUnitOfMeasure().getTitle().toUpperCase());
        else ps.setString(8, null);
    }

    private void fillProductWithOwner(PreparedStatement ps, Product product) throws SQLException {
        ps.setString(1, product.getOwner().getNameOwner());
        ps.setString(2, DateTimeFormatter.ofPattern("dd.MM.yyyy").format(product.getOwner().getBirthday()));
        ps.setLong(3, product.getOwner().getHeight());
        ps.setInt(4, product.getOwner().getWeight());
        ps.setString(5, product.getOwner().getPassportID());

        ps.setInt(6, product.getCoordinates().getX());
        ps.setInt(7, product.getCoordinates().getY());

        ps.setString(8, product.getName());
        ps.setString(9, new SimpleDateFormat("dd.MM.yyyy HH:mm:ss").format(product.getCreationDate()));
        ps.setDouble(10, product.getPrice());
        ps.setString(11, product.getPartNumber());
        ps.setDouble(12, product.getManufactureCost());
        if (product.getUnitOfMeasure() != null) ps.setString(13, product.getUnitOfMeasure().getTitle().toUpperCase());
        else ps.setString(13, null);
    }

    public boolean fillStatementInsert(Product product) throws SQLException {
        if (product.getOwner() != null){
            fillProductWithOwner(insert_with_owner, product);
            insert_with_owner.setString(14, product.getUsername());
            return true;
        } else {
            fillProductWithoutOwner(insert_without_owner, product);
            insert_without_owner.setString(9, product.getUsername());
            return false;
        }
    }

    public boolean fillStatementUpdate(Product product, int id) throws SQLException {
        if (product.getOwner() != null){
            fillProductWithOwner(update_by_id_with_owner, product);
            update_by_id_with_owner.setInt(14, id);
            return true;
        } else {
            fillProductWithoutOwner(update_by_id_without_owner, product);
            update_by_id_without_owner.setInt(9, id);
            return false;
        }
    }

    public void fillInsertLoginData(String login, String password, String paper) throws SQLException{
        String salt = RandomStringUtils.random(10, true, true);
        insert_login_data.setString(1, login);
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            insert_login_data.setString(2, new String(md5.digest((paper + password + salt).getBytes(StandardCharsets.UTF_8)), StandardCharsets.UTF_8));
        } catch (NoSuchAlgorithmException ignore) {}
        insert_login_data.setString(3, salt);
    }

    public void fillSelectSalt(String login) throws SQLException {
        select_salt_by_login.setString(1, login);
    }

    public void fillClearCollection(String login) throws SQLException {
        clear_collection_by_username.setString(1, login);
    }

    public void fillSelectPassword(String login) throws SQLException {
        select_password_by_login.setString(1, login);
    }

    public void fillDeletedById(long id) throws SQLException {
        deleted_by_id.setLong(1, id);
    }
}
