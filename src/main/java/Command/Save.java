package Command;

import Command.Reader.Reader;
import Manager.CollectionManager;

/**
 * a class for saving all element of the collection
 */
public class Save implements SimpleCommand{
    /**
     * a single method for saving collection in file (format XML)
     */
    @Override
    public void execute(CollectionManager manager, Reader reader, String arg) {
        manager.save();
    }
}
