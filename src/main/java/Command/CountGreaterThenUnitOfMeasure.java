package Command;

import Command.CommandFactory.CommandFactory;
import Command.Reader.Reader;
import Content.UnitOfMeasure;
import Manager.CollectionManager;
import Messager.Messenger;
import Exception.InvalidProductFieldException;


/**
 * counts the number of elements large in units
 */
public class CountGreaterThenUnitOfMeasure implements Command{
    /**
     * read unit product
     * then counts and print number
     */
    @Override
    public void execute(CollectionManager manager, Reader reader, String arg, Messenger messanger, CommandFactory commandFactory) {
        UnitOfMeasure unitOfMeasure = UnitOfMeasure.fromString(arg);
        if (unitOfMeasure == null) {
            System.err.println(new InvalidProductFieldException("No such this enum. Unit of measure must be one of: " +
                    UnitOfMeasure.getTitleInString().toLowerCase()).getMessage());
            return;
        }
        System.out.println(messanger.getCountElementWithCondition(manager.countGreaterThenUnitOfMeashure(unitOfMeasure)));
    }
}
