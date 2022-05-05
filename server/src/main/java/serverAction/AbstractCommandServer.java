package serverAction;

import action.Command;
import action.CommandData;
import action.TypeCommand;
import lombok.Getter;

import java.util.Set;

public abstract class AbstractCommandServer implements Command {
    @Getter CommandData commandData;
    @Getter protected ExecutionResources executionResources;
    public AbstractCommandServer(String name, Set<TypeCommand> types, String description){
        commandData = new CommandData(name, types, description);
    }
}
