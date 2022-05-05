package serverAction.commands;

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
                Set.of(TypeCommand.USER),
                "print all elements of the collection in string representation");
        this.executionResources = executionResources;
    }
    /**
     * a single method for showing all elements
     */
    public String execute() {
        StringBuilder builder = new StringBuilder();
        if (executionResources.getCollectionManager().showElements().isEmpty()){
            return "Nothing";
        }else {
            executionResources.getCollectionManager().showElements().forEach(builder::append);
            return builder.toString();
        }
    }
}
