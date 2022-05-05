package Command;

import Command.CommandFactory.CommandFactory;
import Command.Reader.Reader;
import Manager.CollectionManager;
import Messager.Messenger;

/**
 * Clearing collection
 */
public class Clear implements Command {
    /**
     * A single method which clear collection
     */
    @Override
    public void execute(CollectionManager manager, Reader reader, String arg, Messenger messenger, CommandFactory commandFactory) {
        manager.clear();
        System.out.println("Clearing success");
    }
}
