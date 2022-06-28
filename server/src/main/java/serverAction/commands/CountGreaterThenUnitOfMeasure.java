package serverAction.commands;

import action.ResultAction;
import action.State;
import action.TypeCommand;
import content.Product;
import content.UnitOfMeasure;
import exceptions.InvalidProductFieldException;
import serverAction.AbstractCommandServer;
import serverAction.ExecutionResources;

import java.util.List;
import java.util.Set;


/**
 * counts the number of elements large in units
 */
public class CountGreaterThenUnitOfMeasure extends AbstractCommandServer {
    public CountGreaterThenUnitOfMeasure(ExecutionResources executionResources){
        super("CountGreaterThenUnitOfMeasure",
                Set.of(TypeCommand.EXTERNAL, TypeCommand.ARG),
                "display the number of elements whose unitOfMeasure field value is greater than the given one. Here list of the available unit: " + UnitOfMeasure.getTitleInString());
        this.executionResources.set(executionResources);
    }
    /**
     * read unit product
     * then counts and print number
     */
    public ResultAction execute() {
        UnitOfMeasure unitOfMeasure = UnitOfMeasure.fromString(executionResources.get().getArg());
        if (unitOfMeasure == null) {
            return new ResultAction(State.ERROR, "No such this enum. Unit of measure must be one of: " +
                    UnitOfMeasure.getTitleInString().toLowerCase());
        }
        List<Product> products = executionResources.get().getCollectionManager().countGreaterThenUnitOfMeashure(unitOfMeasure);
        ResultAction resultAction =  new ResultAction(State.SUCCESS, "Has been found " +
                products.size()
                + " elements of the collection. \n");
        resultAction.setCollection(products);
        return resultAction;
    }
}
