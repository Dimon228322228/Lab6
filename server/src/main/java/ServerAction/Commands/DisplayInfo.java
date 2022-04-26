package ServerAction.Commands;

import Action.Command;
import Action.TypeCommand;
import Manager.CollectionManager;
import Messager.Messenger;
import lombok.Getter;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * print info about collection
 */
public class DisplayInfo implements Command {
    @Getter final Set<TypeCommand> type = new HashSet<>();
    @Getter final String name = "displayInfo";
    public DisplayInfo(){
        type.add(TypeCommand.USER);
    }
    /**
     * output information about collection: Class, size and date
     */
    public void execute(CollectionManager manager, Messenger messanger) {
        List<String> info = manager.displayInfo();
        System.out.println(messanger.getCollectionMessage(info.get(0), info.get(1), info.get(2)));
    }
}
