package ServerAction.Commands;

import Manager.CollectionManager;
import Messager.Messenger;

/**
 * showing all element of the collection
 */
public class ShowElements{
    /**
     * a single method for showing all elements
     */
    public void execute(CollectionManager manager, Messenger messanger) {
        manager.showElements().forEach(x -> System.out.println(messanger.getProductMessage(x)));
        if (manager.showElements().isEmpty()) System.out.println("Nothing");
    }
}
