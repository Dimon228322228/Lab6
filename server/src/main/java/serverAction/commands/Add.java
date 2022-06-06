package serverAction.commands;

import action.ResultAction;
import action.State;
import action.TypeCommand;
import content.Product;
import exceptions.InvalidProductFieldException;
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
        try {
            if(executionResources.getCollectionManager().add(product))
            return new ResultAction(State.SUCCESS, "Product has been added successful. \n");
            else return new ResultAction(State.ERROR, "Haven't got any product. Nothing adding. \n");
        } catch (SQLException e) {
            return new ResultAction(State.FAILED, "Can't add product to database. \n");
        }
    }
}
