package serverAction.commands;

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
                Set.of(TypeCommand.USER, TypeCommand.PRODUCT, TypeCommand.ARG),
                "remove element from collection by its id");
        this.executionResources = executionResources;
    }
    /**
     * read id from console
     * remove element of the collection by id
     */
    public String execute() throws ProductNotFoundException, InvalidProductFieldException{
        long id;
        try{
            id = Long.parseLong(executionResources.getArg());
            executionResources.getCollectionManager().removeById(id);
        } catch (NumberFormatException e){
            throw new InvalidProductFieldException ("Id must be long!");
        }
        return "A product with id = " + id + " has been removed";
    }
}