package Command;

import Command.CommandFactory.CommandFactory;
import Command.Reader.Reader;
import Manager.CollectionManager;
import Messager.Messenger;

/**
 * a class for saving all element of the collection
 */
public class Save implements Command{
    /**
     * a single method for saving collection in file (format XML)
     */
    @Override
    public void execute(CollectionManager manager, Reader reader, String arg, Messenger messenger, CommandFactory commandFactory) {
        manager.save();
    }
}
