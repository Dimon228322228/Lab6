package ServerAction.Commands;

import Action.Command;
import Action.TypeCommand;
import Manager.CollectionManager;
import lombok.Getter;

import java.util.HashSet;
import java.util.Set;

/**
 * a class for saving all element of the collection
 */
public class Save implements Command {
    @Getter Set<TypeCommand> type = new HashSet<>();
    @Getter final String name = "save";
    /**
     * a single method for saving collection in file (format XML)
     */
    public void execute(CollectionManager manager) {
        manager.save();
    }
}
