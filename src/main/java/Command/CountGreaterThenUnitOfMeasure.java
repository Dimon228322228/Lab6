package Command;

import Command.CommandFactory.CommandFactory;
import Command.Reader.Reader;
import Content.UnitOfMeasure;
import Manager.CollectionManager;
import Messager.Messenger;
import Exception.InvalidUnitOfMeasureException;


/**
 * counts the number of elements large in units
 */
public class CountGreaterThenUnitOfMeasure implements MessagingCommand{
    /**
     * read unit product
     * then counts and print number
     */
    @Override
    public void execute(CollectionManager manager, Reader reader, String arg, Messenger messanger, CommandFactory commandFactory) {
        UnitOfMeasure unitOfMeasure = UnitOfMeasure.fromString(arg);
        if (unitOfMeasure == null) {
            System.err.println(new InvalidUnitOfMeasureException("No such this enum. Unit of measure must be one of: " + UnitOfMeasure.getTitleInString().toLowerCase()).getMessage());
            return;
        }
        long count = manager.countGreaterThenUnitOfMeashure(unitOfMeasure);
        System.out.println(messanger.getCountElementWithCondition(count));
    }
}
