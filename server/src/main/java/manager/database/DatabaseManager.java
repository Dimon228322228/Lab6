package manager.database;

import content.BuilderProduct;
import content.Product;
import exceptions.InvalidProductFieldException;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;

import java.sql.*;
import java.time.DateTimeException;
import java.util.ArrayList;
import java.util.List;

@Log4j2
public class DatabaseManager {
    private Connection connection;
    StatementStorage storage ;
    String link;
    String user;
    String password;
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
        storage = new StatementStorage(connection);
        storage.initPrepareStatement();
        log.info("Has been connected to database. ");
    }

    public void bindToDatabase() throws SQLException {
            connection = DriverManager.getConnection("jdbc:postgresql://" + link, user, password);
    }

    public void executeUpdateById(Product product, int id) throws SQLException {
        if (storage.fillStatementUpdate(product, id)) storage.getUpdate_by_id_with_owner().execute();
        else storage.getUpdate_by_id_without_owner().execute();
        connection.createStatement().execute(storage.getUpdate_dependent_table());
    }

    public long executeInsert(Product product) throws SQLException {
        ResultSet result;
        if (storage.fillStatementInsert(product)) result = storage.getInsert_with_owner().executeQuery();
        else result = storage.getInsert_without_owner().executeQuery();
        result.next();
        return result.getInt("id");
    }

    public void executeInsertAccessData(String login, String password) throws SQLException {
        storage.fillInsertLoginData(login, password, paper);
        storage.getInsert_login_data().executeUpdate();
    }

    public void executeClearCollection (String login) throws SQLException {
        storage.fillClearCollection(login);
        storage.getClear_collection_by_username().execute();
        connection.createStatement().execute(storage.getUpdate_dependent_table());
    }

    public String executeSelectSalt(String login) throws SQLException {
        storage.fillSelectSalt(login);
        ResultSet answer = storage.getSelect_salt_by_login().executeQuery();
        answer.next();
        return answer.getString("salt");
    }

    public String executeSelectPassword(String login) throws SQLException {
        storage.fillSelectPassword(login);
        ResultSet answer = storage.getSelect_password_by_login().executeQuery();
        answer.next();
        return answer.getString("password");
    }

    public void executeDeletedById(long id) throws SQLException {
        storage.fillDeletedById(id);
        storage.getDeleted_by_id().execute();
        connection.createStatement().execute(storage.getUpdate_dependent_table());
    }

    private ResultSet executeSynchronized() throws SQLException {
        return storage.getGet_all().executeQuery();
    }

    private ResultSet executeGetPersonById(int id) throws SQLException {
        storage.getGet_person_by_id().setInt(1, id);
        return storage.getGet_person_by_id().executeQuery();
    }

    public List<Product> getCollectionFromDatabase() throws SQLException {
        ResultSet resultSet = executeSynchronized();
        List<Product> products = new ArrayList<>();
        while (resultSet.next()){
            try {
                BuilderProduct builderProduct = new BuilderProduct();
                builderProduct.setName(resultSet.getString("name"))
                        .setXCoordinate(String.valueOf(resultSet.getInt("x")))
                        .setYCoordinate(String.valueOf(resultSet.getInt("y")))
                        .setCreationDate(resultSet.getString("creationdate"))
                        .setPrice(String.valueOf(resultSet.getDouble("price")))
                        .setPartNumber(resultSet.getString("partnumber"))
                        .setManufactureCost(String.valueOf(resultSet.getDouble("cost")))
                        .setUnitOfMeasure(String.valueOf(resultSet.getObject("unit")))
                        .setUsername(resultSet.getString("username"));
                if (resultSet.getObject("owner") != null) {
                    ResultSet resultSet1 = executeGetPersonById(resultSet.getInt("owner"));
                    resultSet1.next();
                    builderProduct.setPersonName(resultSet1.getString("name"))
                            .setPersonBirthday(resultSet1.getString("birthday"))
                            .setPersonHeight(String.valueOf(resultSet1.getInt("height")))
                            .setPersonWeight(String.valueOf(resultSet1.getInt("weight")))
                            .setPersonPassportId(resultSet1.getString("passportid"));
                }
                Product product = builderProduct.getProduct();
                product.setId(resultSet.getInt("id"));
                products.add(product);
            } catch (InvalidProductFieldException | NumberFormatException | DateTimeException e){
                log.info(e.getMessage());
            }
        }
        return products;
    }
}
