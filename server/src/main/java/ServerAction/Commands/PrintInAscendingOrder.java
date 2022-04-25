package ServerAction.Commands;

import Manager.CollectionManager;
import Messager.Messenger;

/**
 * output all element of the collection in ascending order
 */
public class PrintInAscendingOrder{
    /**
     * a single method for output all element of the collection by ascending order
     */
    public void execute(CollectionManager manager, Messenger messanger) {
        manager.showElements().stream().forEachOrdered(x -> System.out.println(messanger.getProductMessage(x)));
        if (manager.showElements().isEmpty()) System.out.println("Nothing");
    }
}
