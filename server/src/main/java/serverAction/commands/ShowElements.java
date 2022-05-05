package serverAction.commands;

import action.AbstractCommand;
import action.TypeCommand;

import java.util.Set;

/**
 * showing all element of the collection
 */
public class ShowElements extends AbstractCommand {
    public ShowElements(){
        super("show",
                Set.of(TypeCommand.USER),
                "print all elements of the collection in string representation");
    }
    /**
     * a single method for showing all elements
     */
    public void execute() {
//        manager.showElements().forEach(x -> System.out.println(messanger.getProductMessage(x)));
//        if (manager.showElements().isEmpty()) System.out.println("Nothing");
    }
}
