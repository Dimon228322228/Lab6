package serverAction.commands;

import action.AbstractCommand;
import action.TypeCommand;

import java.util.Set;

/**
 * used to describe the actions of the commands
 */
public class Help  extends AbstractCommand {
    public Help(){
        super("help",
                Set.of(TypeCommand.USER),
                "displays reference about all commands");
    }
    /**
     * outputs a description of the commands
     */
    public void execute() {

//        System.out.println(messanger.getCommandsMessage());
    }
}
