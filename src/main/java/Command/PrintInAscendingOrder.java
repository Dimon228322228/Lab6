package Command;

import Command.CommandFactory.CommandFactory;
import Command.Reader.Reader;
import Manager.CollectionManager;
import Messager.Messenger;

/**
 * output all element of the collection in ascending order
 */
public class PrintInAscendingOrder implements Command{
    /**
     * a single method for output all element of the collection by ascending order
     */
    @Override
    public void execute(CollectionManager manager, Reader reader, String arg, Messenger messanger, CommandFactory commandFactory) {
        manager.showElements().stream().forEachOrdered(x -> System.out.println(messanger.getProductMessage(x)));
        if (manager.showElements().isEmpty()) System.out.println("Nothing");
    }
}
