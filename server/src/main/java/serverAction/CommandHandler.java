package serverAction;

import action.CommandData;
import action.ResultAction;
import action.State;
import action.TypeCommand;
import authentication.Account;
import authentication.TypeAuthentication;
import content.Product;
import lombok.Getter;
import lombok.Setter;
import manager.QueueManager;
import manager.database.DatabaseManager;
import serverAction.commands.*;
import transmission.Request;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * A class determines what type of command was entered and stores the command history
 */
public class CommandHandler {
    QueueManager colManag = QueueManager.getInstance();
    final ExecutionResources execRes = new ExecutionResources(colManag);
    @Getter private String authenticationMessage = "";
    @Setter
    Request request;
    private final Map<String, AbstractCommandServer> commandMap = new HashMap<>();

    public CommandHandler() {
        Stream.of(new Add(execRes), new AddIfMax(execRes), new Clear(execRes), new CountByManufactureCost(execRes),
                new CountGreaterThenUnitOfMeasure(execRes), new DisplayInfo(execRes), new PrintInAscendingOrder(execRes),
                new RemoveById(execRes), new RemoveLower(execRes), new ShowElements(execRes), new UpdateById(execRes))
                .forEach(x -> commandMap.put(x.getCommandData().getName(), x));
    }


    public List<CommandData> getCommandDataForUser(){
        return commandMap.keySet().stream()
                .filter(x -> commandMap.get(x).getCommandData().getTypes().contains(TypeCommand.EXTERNAL))
                .map(x -> commandMap.get(x).getCommandData())
                .collect(Collectors.toList());
    }

    /**
     * @return command from string
     */
    public AbstractCommandServer getCommand(String commandName) {
        return commandMap.get(commandName);
    }

    /**
     * receives command form string, checked that it is correct
     * add given command in history if it is correct
     * calls a method to execute a certain type of command
     */
    public ResultAction executeCommand(String commandName, Request request) {
        synchronized(execRes) {
            setRequest(request);
            AbstractCommandServer command = getCommand(commandName);
            setAccount(command);
            bindToDatabase();
            if (!checkAuthentication())
                return new ResultAction(State.FAILED, "You're no log in the server. Please, log in or register with command login. ");
            if (command.getCommandData().getTypes().contains(TypeCommand.ARG)) setArg(command);
            if (command.getCommandData().getTypes().contains(TypeCommand.PRODUCT)) setProduct(command);
            return command.execute();
        }
    }

    private boolean checkAuthentication(){
        Account account = request.getAccount();
        String login = account.getName();
        String password = account.getPassword();
        String salt;
        String pass;
        bindToDatabase();
        if (account.getType().equals(TypeAuthentication.REGISTRATION)) {
            try {
                execRes.getDatabaseManager().executeInsertAccessData(login, password);
                authenticationMessage = "You're successfully registration. ";
                return true;
            } catch (SQLException e) {
                authenticationMessage = "This login yet exist in database or database hasn't responded. ";
                return false;
            }
        }
        try {
            salt = execRes.getDatabaseManager().executeSelectSalt(login);
            pass = execRes.getDatabaseManager().executeSelectPassword(login);
        } catch (SQLException e) {
            authenticationMessage = "No such this login in database or database hasn't responded. ";
            return false;
        }
        String password1 = null;
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            password1 = new String(md5.digest((execRes.getDatabaseManager().getPaper() + password + salt).getBytes(StandardCharsets.UTF_8)), StandardCharsets.UTF_8);
        } catch (NoSuchAlgorithmException ignore) {}
        if (pass.equals(password1)){
            authenticationMessage = "You're successfully log in. ";
            return true;
        } else{
            authenticationMessage = "Password isn't correctness. ";
            return false;
        }
    }

    public void bindToDatabase(){
        try {
            execRes.getDatabaseManager().bindToDatabase();
        } catch (SQLException ignore) {}
    }

    public boolean checkAuthentication(Request request){
        setRequest(request);
        return checkAuthentication();
    }

    private void setArg(AbstractCommandServer command){
        command.getExecutionResources().get().setArg(request.getArg());
    }

    private void setProduct(AbstractCommandServer command){
        command.getExecutionResources().get().setProduct(request.getProduct());
    }

    private void setAccount(AbstractCommandServer command){
        command.getExecutionResources().get().setAccount(request.getAccount());
    }

    public void setDatabaseManagerToExecutionResources(DatabaseManager databaseManager){
        execRes.setDatabaseManager(databaseManager);
        colManag.setDatabaseManager(databaseManager);
    }

    public void synchronizeWithDatabase(){
        List<Product> products = new ArrayList<>();
        try {
            products.addAll(execRes.getDatabaseManager().getCollectionFromDatabase());
        } catch (SQLException e) {
            System.err.println("Something wrong has occurred when collection load. ");
        }
        for (Product product : products) colManag.addWithoutSetCreationDate(product);
    }
}
