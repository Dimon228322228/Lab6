package ServerAction.Commands;

import Messager.Messenger;

/**
 * used to describe the actions of the commands
 */
public class Help {
    /**
     * outputs a description of the commands
     */
    public void execute(Messenger messanger) {
        System.out.println(messanger.getCommandsMessage());
    }
}
