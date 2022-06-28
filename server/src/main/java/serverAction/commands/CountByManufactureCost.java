package serverAction.commands;

import action.ResultAction;
import action.State;
import action.TypeCommand;
import content.Product;
import exceptions.InvalidProductFieldException;
import serverAction.AbstractCommandServer;
import serverAction.ExecutionResources;

import java.util.List;
import java.util.Set;

/**
 * count number of element equals by manufacture cost
 */
public class CountByManufactureCost extends AbstractCommandServer {
    public CountByManufactureCost(ExecutionResources executionResources){
        super("countByManufactureCost",
                Set.of(TypeCommand.EXTERNAL, TypeCommand.ARG),
                "display the number of elements whose value of the manufactureCost field is equal to the specified one");
        this.executionResources.set(executionResources);
    }
    /**
     * read manufacture cost
     * checked it is corrected
     * count number of element
     * print result
     */
    public ResultAction execute() {
        double cost;
        try{
            cost = Double.parseDouble(executionResources.get().getArg());
        } catch (NumberFormatException | NullPointerException e){
            return new ResultAction(State.ERROR, "Manufacture cost must be convert to double. \n");
        }
        List<Product> products = executionResources.get().getCollectionManager().countByManufactureCost(cost);
        ResultAction resultAction = new ResultAction(State.SUCCESS, "Has been found "
                + products.size() +
                " elements of the collection. \n");
        resultAction.setCollection(products);
        return resultAction;
    }
}
