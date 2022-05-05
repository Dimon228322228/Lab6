package Command;

import Command.CommandFactory.CommandFactory;
import Command.Reader.Reader;
import Manager.CollectionManager;
import Messager.Messenger;

/**
 * method execute definition in heirs interfaces
 */
public interface Command {
    void execute(CollectionManager manager, Reader reader, String arg, Messenger messenger, CommandFactory commandFactory);
}
