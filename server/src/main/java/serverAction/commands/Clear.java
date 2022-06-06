package serverAction.commands;

import action.ResultAction;
import action.State;
import action.TypeCommand;
import serverAction.AbstractCommandServer;
import serverAction.ExecutionResources;

import java.sql.SQLException;
import java.util.Set;

/**
 * Clearing collection
 */
public class Clear extends AbstractCommandServer {
    public Clear(ExecutionResources executionResources){
        super("clear",
                Set.of(TypeCommand.EXTERNAL),
                "clear the collection. Note! You can deleted product if you create it. Product that another user has it you can't deleted. ");
        this.executionResources = executionResources;
    }
    /**
     * A single method which clear collection
     */
    public ResultAction execute() {
        try {
            executionResources.getCollectionManager().clearByUsername(executionResources.getAccount().getName());
        } catch (SQLException e) {
            return new ResultAction(State.FAILED, "Can't deleted your collection from database. \n");
        }
        return new ResultAction(State.SUCCESS, "Collection has been clear. \n");
    }
}
