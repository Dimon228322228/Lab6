package ServerAction.Commands;

import Action.Command;
import Action.TypeCommand;
import Manager.CollectionManager;
import Messager.Messenger;
import lombok.Getter;

import java.util.HashSet;
import java.util.Set;

/**
 * showing all element of the collection
 */
public class ShowElements implements Command {
    @Getter final Set<TypeCommand> type = new HashSet<>();
    @Getter final String name = "show";
    public ShowElements(){
        type.add(TypeCommand.USER);
    }
    /**
     * a single method for showing all elements
     */
    public void execute(CollectionManager manager, Messenger messanger) {
        manager.showElements().forEach(x -> System.out.println(messanger.getProductMessage(x)));
        if (manager.showElements().isEmpty()) System.out.println("Nothing");
    }
}
