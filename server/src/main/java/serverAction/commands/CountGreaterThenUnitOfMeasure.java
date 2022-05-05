package serverAction.commands;

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
                Set.of(TypeCommand.USER, TypeCommand.ARG),
                "display the number of elements whose unitOfMeasure field value is greater than the given one");
        this.executionResources = executionResources;
    }
    /**
     * read unit product
     * then counts and print number
     */
    public String execute() throws InvalidProductFieldException {
        UnitOfMeasure unitOfMeasure = UnitOfMeasure.fromString(executionResources.getArg());
        if (unitOfMeasure == null) {
            throw new InvalidProductFieldException("No such this enum. Unit of measure must be one of: " +
                    UnitOfMeasure.getTitleInString().toLowerCase());
        }
        return "Has been found " +
                executionResources.getCollectionManager().countGreaterThenUnitOfMeashure(unitOfMeasure)
                + " elements of the collection. ";
    }
}
