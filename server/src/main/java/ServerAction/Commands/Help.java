package ServerAction.Commands;

import Action.Command;
import Action.TypeCommand;
import Messager.Messenger;
import lombok.Getter;

import java.util.HashSet;
import java.util.Set;

/**
 * used to describe the actions of the commands
 */
public class Help  implements Command {
    @Getter final Set<TypeCommand> type = new HashSet<>();
    @Getter final String name = "help";
    public Help(){
        type.add(TypeCommand.USER);
    }
    /**
     * outputs a description of the commands
     */
    public void execute(Messenger messanger) {
        System.out.println(messanger.getCommandsMessage());
    }
}
