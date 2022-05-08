package serverAction.commands;

import action.ResultAction;
import action.State;
import action.TypeCommand;
import exceptions.InvalidProductFieldException;
import exceptions.ProductNotFoundException;
import serverAction.AbstractCommandServer;
import serverAction.ExecutionResources;

import java.util.Set;

/**
 * removing element by id
 */
public class RemoveById extends AbstractCommandServer {
    public RemoveById(ExecutionResources executionResources){
        super("removeById",
                Set.of(TypeCommand.EXTERNAL, TypeCommand.PRODUCT, TypeCommand.ARG),
                "remove element from collection by its id");
        this.executionResources = executionResources;
    }
    /**
     * read id from console
     * remove element of the collection by id
     */
    public ResultAction execute() throws ProductNotFoundException, InvalidProductFieldException{
        long id;
        try{
            id = Long.parseLong(executionResources.getArg());
            executionResources.getCollectionManager().removeById(id);
        } catch (NumberFormatException e){
            return new ResultAction(State.ERROR, "Id must be long!");
        } catch (ProductNotFoundException e){
            return new ResultAction(State.FAILED, e.getMessage());
        }
        return new ResultAction(State.SUCCESS, "A product with id = " + id + " has been removed");
    }
}