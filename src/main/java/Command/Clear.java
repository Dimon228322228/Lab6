package Command;

import Command.Reader.Reader;
import Manager.CollectionManager;

/**
 * Clearing collection
 */
public class Clear implements SimpleCommand {
    /**
     * A single method which clear collection
     */
    @Override
    public void execute(CollectionManager manager, Reader reader, String arg) {
        manager.clear();
        System.out.println("Clearing success");
    }
}
