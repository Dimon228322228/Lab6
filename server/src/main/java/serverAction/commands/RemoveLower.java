package serverAction.commands;

import action.ResultAction;
import action.State;
import action.TypeCommand;
import content.Product;
import serverAction.AbstractCommandServer;
import serverAction.ExecutionResources;

import java.util.Set;

/**
 * remove elements smaller than the given
 */
public class RemoveLower extends AbstractCommandServer {
    public RemoveLower(ExecutionResources executionResources){
        super("removeLower",
                Set.of(TypeCommand.EXTERNAL, TypeCommand.PRODUCT),
                "remove all elements from the collection that are smaller than the given one");
        this.executionResources = executionResources;
    }
    /**
     * read product from console
     * removes all smaller elements by Comparable
     */
    public ResultAction execute() {
        Product product = executionResources.getProduct();
        if (product == null) return new ResultAction(State.ERROR, "Haven't got any product. Nothing compare. \n");
        int count = executionResources.getCollectionManager().removeLower(product, getExecutionResources().getAccount().getName());
        return new ResultAction(State.SUCCESS, "Removing " + count + " element of the collection. \n");
    }
}
