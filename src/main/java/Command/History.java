package Command;

import Command.CommandFactory.CommandFactory;
import Command.Reader.Reader;
import Manager.CollectionManager;
import Messager.Messenger;

/**
 * History of commands executed
 */
public class History implements Command{
    /**
     * print history of commands executed
     */
    @Override
    public void execute(CollectionManager manager, Reader reader, String arg, Messenger messanger, CommandFactory commandFactory) {
         System.out.println(commandFactory.getHistory());
    }
}
