package serverAction.commands;

import action.AbstractCommand;
import action.TypeCommand;

import java.util.Set;

/**
 * print info about collection
 */
public class DisplayInfo extends AbstractCommand {
    public DisplayInfo(){
        super("info",
                Set.of(TypeCommand.USER),
                "displays information about collection");
    }
    /**
     * output information about collection: Class, size and date
     */
    public void execute() {
//        List<String> info = manager.displayInfo();
//        System.out.println(messanger.getCollectionMessage(info.get(0), info.get(1), info.get(2)));
    }
}
