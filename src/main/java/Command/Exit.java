package Command;

import Command.Reader.Reader;
import Manager.CollectionManager;

/**
 * terminates commands
 */
public class Exit implements SimpleCommand{
    /**
     * stops executing commands
     */
    @Override
    public void execute(CollectionManager manager, Reader reader, String arg) {
        reader.exit();
    }
}
