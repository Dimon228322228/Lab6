package serverAction.commands;

import action.ResultAction;
import action.State;
import action.TypeCommand;
import content.Product;
import exceptions.InvalidProductFieldException;
import exceptions.ProductNotFoundException;
import serverAction.AbstractCommandServer;
import serverAction.ExecutionResources;

import java.sql.SQLException;
import java.util.Set;

/**
 * removing element by id
 */
public class RemoveById extends AbstractCommandServer {
    public RemoveById(ExecutionResources executionResources){
        super("removeById",
                Set.of(TypeCommand.EXTERNAL, TypeCommand.ARG),
                "remove element from collection by its id");
        this.executionResources = executionResources;
    }
    /**
     * read id from console
     * remove element of the collection by id
     */
    public ResultAction execute() throws ProductNotFoundException, InvalidProductFieldException{
        long id;
        Product previousProduct = null;
        try{
            id = Long.parseLong(executionResources.getArg());
            previousProduct = executionResources.getCollectionManager().getById(id);
            executionResources.getCollectionManager().removeById(id, getExecutionResources().getAccount().getName());
            executionResources.getDatabaseManager().executeDeletedById(id);
        } catch (NumberFormatException e){
            return new ResultAction(State.ERROR, "Id must be long! \n");
        } catch (ProductNotFoundException e){
            return new ResultAction(State.FAILED, e.getMessage());
        } catch (SQLException e) {
            executionResources.getCollectionManager().addWithoutSetCreationDate(previousProduct);
            return new ResultAction(State.FAILED, e.getMessage());
        }
        return new ResultAction(State.SUCCESS, "A product with id = " + id + " has been removed. \n");
    }
}