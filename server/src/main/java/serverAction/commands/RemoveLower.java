package serverAction.commands;

import action.TypeCommand;
import content.Product;
import exceptions.InvalidProductFieldException;
import serverAction.AbstractCommandServer;
import serverAction.ExecutionResources;

import java.util.Set;

/**
 * remove elements smaller than the given
 */
public class RemoveLower extends AbstractCommandServer {
    public RemoveLower(ExecutionResources executionResources){
        super("removeLower",
                Set.of(TypeCommand.USER, TypeCommand.PRODUCT),
                "remove all elements from the collection that are smaller than the given one");
        this.executionResources = executionResources;
    }
    /**
     * read product from console
     * removes all smaller elements by Comparable
     */
    public String execute() {
        Product product = executionResources.getProduct();
        if (product == null) throw new InvalidProductFieldException("Haven't got any product. Nothing compare. ");
        int count = executionResources.getCollectionManager().removeLower(product);
        return "Removing " + count + " element of the collection. ";
    }
}
