package Command;

import Command.CommandFactory.CommandFactory;
import Command.Reader.Reader;
import Manager.CollectionManager;
import Messager.Messenger;

import java.util.List;

/**
 * print info about collection
 */
public class DisplayInfo implements Command {
    /**
     * output information about collection: Class, size and date
     */
    @Override
    public void execute(CollectionManager manager, Reader reader, String arg, Messenger messanger, CommandFactory commandFactory) {
        List<String> info = manager.displayInfo();
        System.out.println(messanger.getCollectionMessage(info.get(0), info.get(1), info.get(2)));
    }
}
