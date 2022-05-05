package actionClient;

import action.TypeCommand;

import java.util.Set;

/**
 * used to describe the actions of the commands
 */
public class Help  extends AbstractCommandClient {
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
