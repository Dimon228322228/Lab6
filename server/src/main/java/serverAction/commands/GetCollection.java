package serverAction.commands;

import action.ResultAction;
import action.State;
import serverAction.AbstractCommandServer;
import serverAction.ExecutionResources;

import java.util.Set;

public class GetCollection extends AbstractCommandServer {

    public GetCollection(ExecutionResources executionResources){
        super("getCollection",
                Set.of(),
                "return collection");
        this.executionResources.set(executionResources);
    }

    public ResultAction execute() {
        ResultAction resultAction = new ResultAction(State.SUCCESS, "");
        resultAction.setCollection(executionResources.get().getCollectionManager().showElements());
        return resultAction;
    }
}
