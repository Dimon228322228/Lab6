package ServerAction.Commands;

import Manager.CollectionManager;
import Messager.Messenger;

import java.util.List;

/**
 * print info about collection
 */
public class DisplayInfo {
    /**
     * output information about collection: Class, size and date
     */
    public void execute(CollectionManager manager, Messenger messanger) {
        List<String> info = manager.displayInfo();
        System.out.println(messanger.getCollectionMessage(info.get(0), info.get(1), info.get(2)));
    }
}
