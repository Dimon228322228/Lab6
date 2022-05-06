package actionClient;

import action.Command;
import action.CommandData;
import action.TypeCommand;
import lombok.Getter;

import java.util.Set;

public abstract class AbstractCommandClient implements Command {
    @Getter CommandData commandData;
    protected CommandHandlerClient comHandl;
    public AbstractCommandClient(String name, Set<TypeCommand> types, String description){
        commandData = new CommandData(name, types, description);
    }
    public abstract String  execute();
}
