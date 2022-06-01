package serverAction.commands;

import action.ResultAction;
import action.State;
import action.TypeCommand;
import content.Product;
import serverAction.AbstractCommandServer;
import serverAction.ExecutionResources;

import java.sql.SQLException;
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
        this.executionResources = executionResources;
    }
    /**
     * read product from console
     * removes all smaller elements by Comparable
     */
    public ResultAction execute() {
        Product product = executionResources.getProduct();
        if (product == null) return new ResultAction(State.ERROR, "Haven't got any product. Nothing compare. \n");
        List<Product> deletedList = executionResources.getCollectionManager().removeLower(product, getExecutionResources().getAccount().getName());
        int count = 0;
        for (Product product1 : deletedList) {
            try {
                executionResources.getDatabaseManager().executeDeletedById(product1.getId());
                count++;
            } catch (SQLException e) {
                executionResources.getCollectionManager().addWithoutSetCreationDate(product1);
            }
        }
        if (count != deletedList.size()) return new ResultAction(State.FAILED, "Removing " + count +
                " element of the collection, but some elements hasn't removed because database error occurred.  \n");
        return new ResultAction(State.SUCCESS, "Removing " + deletedList.size() + " element of the collection. \n");
    }
}
