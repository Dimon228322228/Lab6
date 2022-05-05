package serverAction.commands;

import action.AbstractCommand;
import action.TypeCommand;

import java.util.Set;

/**
 * terminates commands
 */
public class Exit extends AbstractCommand {
    public Exit(){
        super("exit",
                Set.of(TypeCommand.USER),
                "terminate program (without saving to file)");
    }
    /**
     * stops executing commands
     */
    public void execute() {

    }
}
