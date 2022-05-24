package serverAction.commands;

import action.ResultAction;
import action.State;
import action.TypeCommand;
import authentication.TypeAuthentication;
import content.Product;
import exceptions.InvalidProductFieldException;
import manager.database.DatabaseManager;
import serverAction.AbstractCommandServer;
import serverAction.ExecutionResources;

import java.sql.SQLException;
import java.util.Set;

/**
 * add element in the collection
 */
public class Add extends AbstractCommandServer {
    public Add(ExecutionResources executionResources){
        super("add",
                Set.of(TypeCommand.EXTERNAL, TypeCommand.PRODUCT),
                "add a new {Product} to the collection");
        this.executionResources = executionResources;
    }
    /**
     * add product in the collection
     */
    public ResultAction execute() throws InvalidProductFieldException{
        Product product = executionResources.getProduct();
        if (product == null) return new ResultAction(State.ERROR, "Haven't got any product. Nothing adding. \n");
        executionResources.getCollectionManager().add(product);
        if (!addingToDatabase(executionResources.getDatabaseManager(), product)){
            executionResources.getCollectionManager().remove(product);
            return new ResultAction(State.FAILED, "Can't add product to database. \n");
        } else return new ResultAction(State.SUCCESS, "Product has been added successful. \n");
    }

    private boolean addingToDatabase(DatabaseManager databaseManager, Product product){
        try {
            product.setId(databaseManager.executeInsert(product));
            return true;
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            return false;
        }
    }
}
