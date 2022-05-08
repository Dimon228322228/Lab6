package serverAction.commands;

import action.ResultAction;
import action.State;
import action.TypeCommand;
import content.UnitOfMeasure;
import exceptions.InvalidProductFieldException;
import serverAction.AbstractCommandServer;
import serverAction.ExecutionResources;

import java.util.Set;


/**
 * counts the number of elements large in units
 */
public class CountGreaterThenUnitOfMeasure extends AbstractCommandServer {
    public CountGreaterThenUnitOfMeasure(ExecutionResources executionResources){
        super("CountGreaterThenUnitOfMeasure",
                Set.of(TypeCommand.EXTERNAL, TypeCommand.ARG),
                "display the number of elements whose unitOfMeasure field value is greater than the given one");
        this.executionResources = executionResources;
    }
    /**
     * read unit product
     * then counts and print number
     */
    public ResultAction execute() throws InvalidProductFieldException {
        UnitOfMeasure unitOfMeasure = UnitOfMeasure.fromString(executionResources.getArg());
        if (unitOfMeasure == null) {
            return new ResultAction(State.ERROR, "No such this enum. Unit of measure must be one of: " +
                    UnitOfMeasure.getTitleInString().toLowerCase());
        }
        return new ResultAction(State.SUCCESS, "Has been found " +
                executionResources.getCollectionManager().countGreaterThenUnitOfMeashure(unitOfMeasure)
                + " elements of the collection. ");
    }
}
