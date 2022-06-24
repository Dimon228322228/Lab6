package actionClient;

import action.Command;
import action.CommandData;
import action.ResultAction;
import action.TypeCommand;
import lombok.Getter;
import lombok.Setter;
import utilites.LanguageManager;

import java.util.Set;

public abstract class AbstractCommandClient implements Command {
    @Getter CommandData commandData;
    protected CommandController comContr;
    public AbstractCommandClient(String name, Set<TypeCommand> types, String description){
        commandData = new CommandData(name, types, description);
    }
    public abstract ResultAction execute();
}
