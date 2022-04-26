package ServerAction.Commands;

import Action.Command;
import Action.TypeCommand;
import Manager.CollectionManager;
import lombok.Getter;

import java.util.HashSet;
import java.util.Set;

/**
 * Clearing collection
 */
public class Clear implements Command {
    @Getter final Set<TypeCommand> type = new HashSet<>();
    @Getter final String name = "clear";
    public Clear(){
        type.add(TypeCommand.USER);
    }
    /**
     * A single method which clear collection
     */
    public void execute(CollectionManager manager) {
        manager.clear();
        System.out.println("Clearing success");
    }
}
