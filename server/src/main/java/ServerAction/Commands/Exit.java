package ServerAction.Commands;

import Action.Command;
import Action.TypeCommand;
import ServerAction.FileReader;
import lombok.Getter;

import java.util.HashSet;
import java.util.Set;

/**
 * terminates commands
 */
public class Exit implements Command {
    @Getter final Set<TypeCommand> type = new HashSet<>();
    @Getter final String name = "exit";
    public Exit(){
        type.add(TypeCommand.USER);
    }
    /**
     * stops executing commands
     */
    public void execute(FileReader reader) {
        reader.exit();
    }
}
