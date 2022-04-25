package ServerAction.Commands;

import Content.UnitOfMeasure;
import Exception.InvalidProductFieldException;
import Manager.CollectionManager;
import Messager.Messenger;


/**
 * counts the number of elements large in units
 */
public class CountGreaterThenUnitOfMeasure{
    /**
     * read unit product
     * then counts and print number
     */
    public void execute(CollectionManager manager, String arg, Messenger messanger) {
        UnitOfMeasure unitOfMeasure = UnitOfMeasure.fromString(arg);
        if (unitOfMeasure == null) {
            System.err.println(new InvalidProductFieldException("No such this enum. Unit of measure must be one of: " +
                    UnitOfMeasure.getTitleInString().toLowerCase()).getMessage());
            return;
        }
        System.out.println(messanger.getCountElementWithCondition(manager.countGreaterThenUnitOfMeashure(unitOfMeasure)));
    }
}
