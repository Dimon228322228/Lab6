package serverAction.commands;

import action.TypeCommand;
import serverAction.AbstractCommandServer;
import serverAction.ExecutionResources;

import java.util.Set;

/**
 * Clearing collection
 */
public class Clear extends AbstractCommandServer {
    public Clear(ExecutionResources executionResources){
        super("clear",
                Set.of(TypeCommand.USER),
                "clear the collection");
        this.executionResources = executionResources;
    }
    /**
     * A single method which clear collection
     */
    public String execute() {
        executionResources.getCollectionManager().clear();
        return "Collection has been clear. ";
    }
}
