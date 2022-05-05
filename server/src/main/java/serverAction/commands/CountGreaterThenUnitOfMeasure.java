package serverAction.commands;

import action.AbstractCommand;
import action.TypeCommand;

import java.util.Set;


/**
 * counts the number of elements large in units
 */
public class CountGreaterThenUnitOfMeasure extends AbstractCommand {
    public CountGreaterThenUnitOfMeasure(){
        super("CountGreaterThenUnitOfMeasure",
                Set.of(TypeCommand.USER, TypeCommand.ARG),
                "display the number of elements whose unitOfMeasure field value is greater than the given one");
    }
    /**
     * read unit product
     * then counts and print number
     */
    public void execute() {
//        UnitOfMeasure unitOfMeasure = UnitOfMeasure.fromString(arg);
//        if (unitOfMeasure == null) {
//            System.err.println(new InvalidProductFieldException("No such this enum. Unit of measure must be one of: " +
//                    UnitOfMeasure.getTitleInString().toLowerCase()).getMessage());
//            return;
//        }
//        System.out.println(messanger.getCountElementWithCondition(manager.countGreaterThenUnitOfMeashure(unitOfMeasure)));
    }
}
