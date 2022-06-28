package serverAction.commands;

import action.ResultAction;
import action.State;
import action.TypeCommand;
import content.Product;
import serverAction.AbstractCommandServer;
import serverAction.ExecutionResources;

import java.util.List;
import java.util.Set;

/**
 * remove elements smaller than the given
 */
public class RemoveLower extends AbstractCommandServer {
    public RemoveLower(ExecutionResources executionResources){
        super("removeLower",
                Set.of(TypeCommand.EXTERNAL, TypeCommand.PRODUCT),
                "remove all elements from the collection that are smaller than the given one");
        this.executionResources.set(executionResources);
    }
    /**
     * read product from console
     * removes all smaller elements by Comparable
     */
    public ResultAction execute() {
        Product product = executionResources.get().getProduct();
        if (product == null) return new ResultAction(State.ERROR, "Haven't got any product. Nothing compare. \n");
        List<Product> products = executionResources.get().getCollectionManager().removeLower(product, getExecutionResources().get().getAccount().getName());
        ResultAction resultAction = new ResultAction(State.SUCCESS, "Removing " + products.size() + " element of the collection. \n");
        resultAction.setCollection(products);
        return resultAction;
    }
}
