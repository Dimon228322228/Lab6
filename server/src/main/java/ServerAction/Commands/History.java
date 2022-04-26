package ServerAction.Commands;

import Action.Command;
import Action.TypeCommand;
import ServerAction.CommandFactory.CommandFactory;
import lombok.Getter;

import java.util.HashSet;
import java.util.Set;

/**
 * History of commands executed
 */
public class History implements Command {
    @Getter final Set<TypeCommand> type = new HashSet<>();
    @Getter final String name = "history";
    public History(){
        type.add(TypeCommand.USER);
    }
    /**
     * print history of commands executed
     */
    public void execute(CommandFactory commandFactory) {
         System.out.println(commandFactory.getHistory());
    }
}
