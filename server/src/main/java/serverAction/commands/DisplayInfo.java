package serverAction.commands;

import action.TypeCommand;
import serverAction.AbstractCommandServer;
import serverAction.ExecutionResources;

import java.util.List;
import java.util.Set;

/**
 * print info about collection
 */
public class DisplayInfo extends AbstractCommandServer {
    public DisplayInfo(ExecutionResources executionResources){
        super("info",
                Set.of(TypeCommand.USER),
                "displays information about collection");
        this.executionResources = executionResources;
    }
    /**
     * output information about collection: Class, size and date
     */
    public String execute() {
        List<String> info = executionResources.getCollectionManager().displayInfo();
        return "Collection type: " + info.get(0) +
                "\nCollection size: " + info.get(1) +
                "\nInitialization time: " + info.get(2);
    }
}
