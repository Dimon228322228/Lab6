package serverAction.commands;

import action.AbstractCommand;
import action.TypeCommand;

import java.util.Set;

/**
 * Clearing collection
 */
public class Clear extends AbstractCommand {
    public Clear(){
        super("clear",
                Set.of(TypeCommand.USER),
                "clear the collection");
    }
    /**
     * A single method which clear collection
     */
    public void execute() {
//        manager.clear();
//        System.out.println("Clearing success");
    }
}
