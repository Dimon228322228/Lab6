package Command;

import Command.CommandFactory.CommandFactory;
import Command.Reader.Reader;
import Manager.CollectionManager;
import Messager.Messenger;

/**
 * used to describe the actions of the commands
 */
public class Help implements Command {
    /**
     * outputs a description of the commands
     */
    @Override
    public void execute(CollectionManager manager, Reader reader, String arg, Messenger messanger, CommandFactory commandFactory) {
        System.out.println(messanger.getCommandsMessage());
    }
}
