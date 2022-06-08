package serverAction;

import action.Command;
import action.CommandData;
import action.ResultAction;
import action.TypeCommand;
import lombok.Getter;

import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;

public abstract class AbstractCommandServer implements Command {
    @Getter CommandData commandData;
    @Getter protected volatile AtomicReference<ExecutionResources> executionResources = new AtomicReference<>();
    public AbstractCommandServer(String name, Set<TypeCommand> types, String description){
        commandData = new CommandData(name, types, description);
    }
    public abstract ResultAction execute();
}
