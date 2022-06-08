package serverAction.commands;

import action.ResultAction;
import action.State;
import action.TypeCommand;
import serverAction.AbstractCommandServer;
import serverAction.ExecutionResources;

import java.util.Set;

/**
 * showing all element of the collection
 */
public class ShowElements extends AbstractCommandServer {
    public ShowElements(ExecutionResources executionResources){
        super("show",
                Set.of(TypeCommand.EXTERNAL),
                "print all elements of the collection in string representation");
        this.executionResources.set(executionResources);
    }
    /**
     * a single method for showing all elements
     */
    public ResultAction execute() {
        StringBuilder builder = new StringBuilder();
        if (executionResources.get().getCollectionManager().showElements().isEmpty()){
            return new ResultAction(State.FAILED, "Nothing. \n");
        }else {
            executionResources.get().getCollectionManager().showElements().forEach(builder::append);
            return new ResultAction(State.SUCCESS, builder.toString());
        }
    }
}
