package serverAction.commands;

import action.ResultAction;
import action.State;
import action.TypeCommand;
import content.Product;
import manager.database.DatabaseManager;
import serverAction.AbstractCommandServer;
import serverAction.ExecutionResources;

import java.sql.SQLException;
import java.util.Set;

/**
 * add new element in the collection if it is large max element of the collection
 */
public class AddIfMax extends AbstractCommandServer {
    public AddIfMax(ExecutionResources executionResources){
        super("addIfMax",
                Set.of(TypeCommand.EXTERNAL, TypeCommand.PRODUCT),
                "add a new element to the collection if its value is greater than the value of the largest element in this collection");
        this.executionResources = executionResources;
    }
    /**
     * read product from the console
     * compare it with max product
     * add given product in the collection if it is large max element
     */
    public ResultAction execute() {
        Product product = executionResources.getProduct();
        if (product == null) return new ResultAction(State.ERROR, "Haven't got any product. Nothing compare and add. \n");
        if (executionResources.getCollectionManager().addIfMax(product)) {
            if (!addingToDatabase(executionResources.getDatabaseManager(), product)){
                executionResources.getCollectionManager().remove(product);
                return new ResultAction(State.FAILED, "Can't add product to database. \n");
            }
            return new ResultAction(State.SUCCESS, "The product has been added. \n");
        }
        else return new ResultAction(State.FAILED, "The product hasn't been added because it is not largest. \n");
    }

    private boolean addingToDatabase(DatabaseManager databaseManager, Product product){
        try {
            product.setId(databaseManager.executeInsert(product));
            return true;
        } catch (SQLException e) {
            return false;
        }
    }
}
