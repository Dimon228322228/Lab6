package serverAction.commands;

import action.ResultAction;
import action.State;
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
                Set.of(TypeCommand.EXTERNAL),
                "clear the collection");
        this.executionResources = executionResources;
    }
    /**
     * A single method which clear collection
     */
    public ResultAction execute() {
        executionResources.getCollectionManager().clear();
        return new ResultAction(State.SUCCESS, "Collection has been clear. ");
    }
}
