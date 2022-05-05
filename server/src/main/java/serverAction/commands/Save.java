package serverAction.commands;

import action.AbstractCommand;

import java.util.Set;

/**
 * a class for saving all element of the collection
 */
public class Save extends AbstractCommand {
    public Save(){
        super("save",
                Set.of(),
                "save collection to file");
    }
    /**
     * a single method for saving collection in file (format XML)
     */
    public void execute() {
//        manager.save();
    }
}
