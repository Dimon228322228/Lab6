package Command;

import Command.CommandFactory.CommandFactory;
import Command.Reader.Reader;
import Manager.CollectionManager;
import Messager.Messenger;

/**
 * a general interface for describing commands that output any information to the console
 */
public interface MessagingCommand extends Command{
    void execute(CollectionManager manager, Reader reader, String arg, Messenger messanger, CommandFactory commandFactory);
}
