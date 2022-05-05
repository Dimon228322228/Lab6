package action;

import lombok.Getter;

import java.util.Set;

public abstract class AbstractCommand implements Command{
    @Getter CommandData commandData;
    public AbstractCommand(String name, Set<TypeCommand> types, String description){
        commandData = new CommandData(name, types, description);
    }
    public abstract void execute();
}
