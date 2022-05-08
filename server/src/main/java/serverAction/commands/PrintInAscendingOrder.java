package serverAction.commands;

import action.ResultAction;
import action.State;
import action.TypeCommand;
import serverAction.AbstractCommandServer;
import serverAction.ExecutionResources;

import java.util.Set;

/**
 * output all element of the collection in ascending order
 */
public class PrintInAscendingOrder extends AbstractCommandServer {
    public PrintInAscendingOrder(ExecutionResources executionResources){
        super("printInAscendingOrder",
                Set.of(TypeCommand.EXTERNAL),
                "display the elements of the collection in ascending order");
        this.executionResources = executionResources;
    }
    /**
     * a single method for output all element of the collection by ascending order
     */
    public ResultAction execute() {
        StringBuilder builder = new StringBuilder();
        if (executionResources.getCollectionManager().showElements().isEmpty()){
            return new ResultAction(State.FAILED, "Nothing");
        }else {
            executionResources.getCollectionManager().showElements().stream().forEachOrdered(builder::append);
            return new ResultAction(State.SUCCESS, builder.toString());
        }
    }
}
