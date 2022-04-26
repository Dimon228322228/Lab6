package ServerAction.Commands;

import Action.Command;
import Action.TypeCommand;
import Manager.CollectionManager;
import Messager.Messenger;
import lombok.Getter;

import java.util.HashSet;
import java.util.Set;

/**
 * output all element of the collection in ascending order
 */
public class PrintInAscendingOrder implements Command {
    @Getter final Set<TypeCommand> type = new HashSet<>();
    @Getter final String name = "printInAscendingOrder";
    public PrintInAscendingOrder(){
        type.add(TypeCommand.USER);
    }
    /**
     * a single method for output all element of the collection by ascending order
     */
    public void execute(CollectionManager manager, Messenger messanger) {
        manager.showElements().stream().forEachOrdered(x -> System.out.println(messanger.getProductMessage(x)));
        if (manager.showElements().isEmpty()) System.out.println("Nothing");
    }
}
