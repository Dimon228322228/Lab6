package serverAction.commands;

import action.AbstractCommand;
import action.TypeCommand;

import java.util.Set;

/**
 * History of commands executed
 */
public class History extends AbstractCommand {
    public History(){
        super("history",
                Set.of(TypeCommand.USER),
                "print the last 13 commands (without their arguments)");
    }
    /**
     * print history of commands executed
     */
    public void execute() {
//         System.out.println(commandFactory.getHistory());
    }
}
