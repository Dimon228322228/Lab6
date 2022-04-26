package ServerAction.Commands;

import Action.Command;
import Action.TypeCommand;
import Content.UnitOfMeasure;
import Exceptions.InvalidProductFieldException;
import Manager.CollectionManager;
import Messager.Messenger;
import lombok.Getter;

import java.util.HashSet;
import java.util.Set;


/**
 * counts the number of elements large in units
 */
public class CountGreaterThenUnitOfMeasure implements Command {
    @Getter final Set<TypeCommand> type = new HashSet<>();
    @Getter final String name = "CountGreaterThenUnitOfMeasure";
    public CountGreaterThenUnitOfMeasure(){
        type.add(TypeCommand.USER);
        type.add(TypeCommand.ARG);
    }
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
